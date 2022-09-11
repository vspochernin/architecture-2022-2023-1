#include "mainwindow.h"
#include "ui_mainwindow.h"

#include "SystemController.h"

#include <QTimer>

MainWindow::MainWindow(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::MainWindow)
{
    ui->setupUi(this);
    ui->autoProgressBar->setValue(0);
    ui->autoProgressBar->setVisible(false);
    initStepByStepTable();
    initStepByStepBufferTable();
    initStepByStepProcessingDevicesTable();
    initAutoSourcesTable();
    initAutoProcessorTable();
    connect(ui->nextStepButton, &QPushButton::clicked,
            this, &MainWindow::onNextStepButton);
    connect(ui->startAutoButton, &QPushButton::clicked,
            this, &MainWindow::onStartAutoButton);
    connect(ui->sourcesNumberSpinBox, SIGNAL(valueChanged(int)),
            this, SLOT(onSourcesCountChange(int)));
    connect(ui->processingNumberSpinBox, SIGNAL(valueChanged(int)),
            this, SLOT(onProcessingCountChange(int)));
    onSourcesCountChange(ui->sourcesNumberSpinBox->value());
    onProcessingCountChange(ui->processingNumberSpinBox->value());
}

MainWindow::~MainWindow()
{
    delete ui;
}

void MainWindow::initStepByStepTable()
{
    QTableWidget *table = ui->stateTableWidget;
    table->setColumnCount(3);
    table->setColumnWidth(0, 75);
    table->setColumnWidth(1, 190);
    table->setColumnWidth(2, 210);
    QStringList headerList;
    headerList << "Время" << "Действие" << "Назначенное устройство";
    table->setHorizontalHeaderLabels(headerList);
    table->setEditTriggers(QAbstractItemView::NoEditTriggers);
}

void MainWindow::initStepByStepBufferTable()
{
    QTableWidget *table = ui->bufferStateTableWidget;
    table->setColumnCount(3);
    QStringList headerList;
    headerList << "№ в буфере" << "№ источника" << "№ заявки";
    table->verticalHeader()->setVisible(false);
    table->setColumnWidth(0, 120);
    table->setColumnWidth(1, 120);
    table->setColumnWidth(2, 120);
    table->setHorizontalHeaderLabels(headerList);
    table->setEditTriggers(QAbstractItemView::NoEditTriggers);
}

void MainWindow::initStepByStepProcessingDevicesTable()
{
    QTableWidget *table = ui->processingStateTableWidget;
    table->setColumnCount(4);
    QStringList headerList;
    headerList << "№" << "Состояние" << "Время начала" << "№ заявки";
    table->verticalHeader()->setVisible(false);
    table->setColumnWidth(0, 80);
    table->setColumnWidth(1, 120);
    table->setColumnWidth(2, 140);
    table->setColumnWidth(3, 120);
    table->setHorizontalHeaderLabels(headerList);
    table->setEditTriggers(QAbstractItemView::NoEditTriggers);
}

void MainWindow::initAutoSourcesTable()
{
    QTableWidget *table = ui->generatorStatTableWidget;
    table->setColumnCount(6);
    table->setColumnWidth(0, 180);
    table->setColumnWidth(1, 180);
    table->setColumnWidth(2, 180);
    table->setColumnWidth(3, 180);
    table->setColumnWidth(4, 180);
    table->setColumnWidth(5, 220);
    QStringList headerList;
    headerList << "Количество заявок" << "Вероятность отказа" << "Время в системе"
               << "Время в буфере" << "Дисперсия буфера" << "Дисперсия обслуживания";
    table->setHorizontalHeaderLabels(headerList);
    table->setEditTriggers(QAbstractItemView::NoEditTriggers);
}

void MainWindow::initAutoProcessorTable()
{
    QTableWidget *table = ui->processorStatTableWidget;
    table->setColumnCount(1);
    table->setColumnWidth(0, 240);
    QStringList headerList;
    headerList << "Коэффициент использования" ;
    table->setHorizontalHeaderLabels(headerList);
    table->setEditTriggers(QAbstractItemView::NoEditTriggers);
}

void MainWindow::onNextStepButton()
{
    ui->firstLevelTabs->setTabEnabled(0, false);
    if (controller == nullptr) {
        controller = new SystemController(ui->poissonSpinBox->value(), ui->normMinSpinBox->value(),
                                          ui->normMaxSpinBox->value(), ui->bufferSizeSpinBox->value(),
                                          ui->totalTasksRequired->value());
    }
    std::multiset<SystemEvent> *eventList = controller->goToNextState();
    std::multiset<std::shared_ptr<OperatingDevice>, OperatingDevice::MyCompare> *operatingDevicesList
            = controller->getOperatingDevices();
    int i = 0;
    QTableWidget *stateTable = ui->stateTableWidget;
    QTableWidget *processingStateTable = ui->processingStateTableWidget;
    QTableWidget *bufferStateTable = ui->bufferStateTableWidget;

    stateTable->setRowCount(eventList->size());
    for (SystemEvent event: *eventList) {
        stateTable->setItem(i, 0, new QTableWidgetItem(QString::number(event.getEventTime())));
        switch (event.getEventType()) {
        case GenerateTask:
            stateTable->setItem(i, 1, new QTableWidgetItem("Создать задачу"));
            break;
        case TaskUnbuffer:
            stateTable->setItem(i, 1, new QTableWidgetItem("Переместить задачу из буфера"));
            break;
        case TaskCompleted:
            stateTable->setItem(i, 1, new QTableWidgetItem("Окончить задачу"));
            break;
        }
        stateTable->setItem(i, 2, new QTableWidgetItem(QString::number(event.getAssignedDevice())));
        i++;
    }

    i = 0;
    processingStateTable->setRowCount(operatingDevicesList->size());
    for (auto myIter = operatingDevicesList->begin(); myIter != operatingDevicesList->end(); myIter++) {
        processingStateTable->setItem(i, 0, new QTableWidgetItem(QString::number((*myIter)->getDeviceNumber())));
        if((*myIter)->isAvaliable()) {
            processingStateTable->setItem(i, 1, new QTableWidgetItem("Свободен"));
            processingStateTable->setItem(i, 3, new QTableWidgetItem(QString::fromStdString("Нет заявки")));
        } else {
            processingStateTable->setItem(i, 1, new QTableWidgetItem("Занят"));
			processingStateTable->setItem(i, 3, new QTableWidgetItem(QString::fromStdString((*myIter)->getCurrentTask()->getTaskId())));
        }
        processingStateTable->setItem(i, 2, new QTableWidgetItem(QString::number((*myIter)->getTaskStartTime())));
        i++;
    }

    i = 0;
    std::vector<MyTaskPointer> taskVector = controller->getBufferQueue()->getTaskVector();
    bufferStateTable->setRowCount(taskVector.size());
    for (auto myIter = taskVector.begin(); myIter != taskVector.end(); myIter++) {
        bufferStateTable->setItem(i, 0, new QTableWidgetItem(QString::number(i)));
        if (*myIter == nullptr) {
            bufferStateTable->setItem(i, 1, new QTableWidgetItem("Свободен"));
            bufferStateTable->setItem(i, 2, new QTableWidgetItem("Свободен"));
            i++;
            continue;
        }
        bufferStateTable->setItem(i, 1, new QTableWidgetItem(QString::number((*myIter)->getSourceNumber())));
        bufferStateTable->setItem(i, 2, new QTableWidgetItem(QString::fromStdString((*myIter)->getTaskId())));
        i++;
    }

    ui->stepProgressBar->setValue((statistics->getTotalTasksProcessed()*1.0 / ui->totalTasksRequired->value()) * 100);
    ui->autoProgressBar->setValue((statistics->getTotalTasksProcessed()*1.0 / ui->totalTasksRequired->value()) * 100);
}

void MainWindow::onStartAutoButton()
{
    ui->firstLevelTabs->setTabEnabled(0, false);
    ui->startAutoButton->setVisible(false);
    ui->autoProgressBar->setVisible(true);
    QTimer* timer = new QTimer();
    timer->setInterval(10); //Time in milliseconds
    timer->setSingleShot(false);
    connect(timer, &QTimer::timeout, this, [=](){
        //Do your stuff in here, gets called every interval time
       ui->stepProgressBar->setValue((statistics->getTotalTasksProcessed()*1.0 / ui->totalTasksRequired->value()) * 100);
       ui->autoProgressBar->setValue((statistics->getTotalTasksProcessed()*1.0 / ui->totalTasksRequired->value()) * 100);
    });

    if (controller == nullptr) {
        controller = new SystemController(ui->poissonSpinBox->value(), ui->normMinSpinBox->value(),
                                          ui->normMaxSpinBox->value(), ui->bufferSizeSpinBox->value(),
                                          ui->totalTasksRequired->value());
    }
    timer->start();
    controller->executeAutoMode();
    timer->stop();
    delete(timer);
    ui->stepProgressBar->setValue(100);
    ui->autoProgressBar->setValue(100);
    QTableWidget *generatorTable = ui->generatorStatTableWidget;
    QTableWidget *processorTable = ui->processorStatTableWidget;

    generatorTable->setRowCount(statistics->getSourceDevicesCount());
    processorTable->setRowCount(statistics->getOperatingDevicesCount());


    int i = 0;
    for (SourceStat generator: statistics->getSourceDevicesVector()) {
        long totalTasks = generator.getGeneratedTasksCount();
        generatorTable->setItem(i, 0, new QTableWidgetItem(QString::number(totalTasks)));
        generatorTable->setItem(i, 1, new QTableWidgetItem(
                                    QString::number(generator.getRejectedTasksCount() * 1.0 / totalTasks)));
        generatorTable->setItem(i, 2, new QTableWidgetItem(QString::number(generator.getTasksTotalTime() * 1.0 / totalTasks)));
        generatorTable->setItem(i, 3, new QTableWidgetItem(
                                    QString::number((generator.getBufferedTime() * 1.0) / totalTasks)));
        generatorTable->setItem(i, 4, new QTableWidgetItem(QString::number(generator.getBufferedTimeDispersion())));
        generatorTable->setItem(i, 5, new QTableWidgetItem(QString::number(generator.getTotalTimeDispersion())));
        i++;
    }
    i = 0;
    for (OperatingStat operatingDevice: statistics->getOperatingDevicesVector()) {
        processorTable->setItem(i, 0, new QTableWidgetItem(QString::number(
                                                               operatingDevice.getTotalWorkingTime() * 1.0 / controller->getCurrentTime())));
        i++;
    }
}

void MainWindow::onSourcesCountChange(int value)
{
    statistics->setSourceDevicesCount(value);
}

void MainWindow::onProcessingCountChange(int value)
{
    statistics->setOperatingDevicesCount(value);
}
