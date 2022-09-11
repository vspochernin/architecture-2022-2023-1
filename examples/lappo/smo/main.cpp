#define VARIANT 3

#include "mainwindow.h"
#include <QApplication>
#include <iostream>

int main(int argc, char *argv[])
{
	QApplication a(argc, argv);
    MainWindow w;
    w.showFullScreen();

    return a.exec();
}
