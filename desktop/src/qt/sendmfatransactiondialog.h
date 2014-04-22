#ifndef SENDMFATRANSACTIONDIALOG_H
#define SENDMFATRANSACTIONDIALOG_H

#include <QDialog>
#include <QImage>
#include <string>
#include <iostream>
#include "key.h"
#include "util.h"

namespace Ui {
	class SendMFATransactionDialog;
}

class SendMFATransactionDialog : public QDialog
{
	Q_OBJECT

	public:
    explicit SendMFATransactionDialog(QWidget *parent = 0);
		~SendMFATransactionDialog();
		void setQRCode(std::string qrCodeStr);

	public slots:
		//void textChangedSlot(QString text);

	private:
		Ui::SendMFATransactionDialog *ui;

	private slots:
		void on_buttonBox_clicked();
};

#endif // SENDMFATRANSACTIONDIALOG_H
