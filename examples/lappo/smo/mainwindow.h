#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include "SystemController.h"

#include <QMainWindow>

namespace Ui {
class MainWindow;
}

class MainWindow : public QMainWindow
{
    Q_OBJECT

public:
    explicit MainWindow(QWidget *parent = 0);
    ~MainWindow();

private:
    Ui::MainWindow *ui;
    SystemController *controller = nullptr;
    void initStepByStepTable();
    void initStepByStepBufferTable();
    void initStepByStepSourcesTable();
    void initStepByStepProcessingDevicesTable();
    void initAutoSourcesTable();
    void initAutoProcessorTable();

    StatisticsController::StatisticsControllerPointer statistics = StatisticsController::getInstance();
private slots:
    void onNextStepButton();
    void onStartAutoButton();
    void onSourcesCountChange(int value);
    void onProcessingCountChange(int value);
};

#endif // MAINWINDOW_H
