#ifndef ASKDEVICEPUBLICKEY_H
#define ASKDEVICEPUBLICKEY_H

#include <QDialog>
#include <string>
#include <iostream>
#include "key.h"

namespace Ui {
	class AskDevicePublicKey;
}

class AskDevicePublicKey : public QDialog
{
	Q_OBJECT

	public:
		explicit AskDevicePublicKey(QWidget *parent = 0);
		~AskDevicePublicKey();
		bool getPubKey(CPubKey &myCPubKey);

	public slots:
		void textChangedSlot(QString text);

	private:
		Ui::AskDevicePublicKey *ui;
		CPubKey cPubKey;

	private slots:
		void on_buttonBox_clicked();
};

#endif // ASKDEVICEPUBLICKEY_H
