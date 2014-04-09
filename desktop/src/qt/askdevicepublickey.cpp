#include "askdevicepublickey.h"
#include "ui_askdevicepublickey.h"

AskDevicePublicKey::AskDevicePublicKey(QWidget *parent) :
    QDialog(parent),
    ui(new Ui::AskDevicePublicKey)
{
	ui->setupUi(this);
	ui->buttonBox->setVisible(false);//button(QDialogButtonBox::Ok)->setEnabled(false);
	connect(ui->devicePubKeyBox, SIGNAL(textChanged(QString)), this, SLOT(textChangedSlot(QString)));
}

AskDevicePublicKey::~AskDevicePublicKey()
{
    delete ui;
}

void AskDevicePublicKey::textChangedSlot(QString text)
{
	std::string pubKey = text.toStdString();
	if (IsHex(pubKey))
	{
		std::cout << "Hex detected\n";
		std::vector<unsigned char> pubKeyData(pubKey.begin(), pubKey.end());
			CPubKey cPubKeyBuf(ParseHex(pubKey));
			//cPubKey = cPubKeyBuf;
			std::cout << "Public key is currently " << pubKey << " (size " << cPubKeyBuf.size() << ")";

			if (cPubKeyBuf.IsValid())
			{
				std::cout << "Public key is valid: " << cPubKeyBuf.begin() << "\n";
				ui->buttonBox->setVisible(true);//->button(QDialogButtonBox::Ok)->setEnabled(true);
			} else
			{
				std::cout << "Public key is not valid: " << cPubKeyBuf.begin() << "\n";
				ui->buttonBox->setVisible(false);//->button(QDialogButtonBox::Ok)->setEnabled(false);
			}
	} else {
		std::cout << "Not a hex\n";
	}
}

void AskDevicePublicKey::on_buttonBox_clicked()
{
	close();
}

bool AskDevicePublicKey::getPubKey(CPubKey &myCPubKey)
{
	myCPubKey = cPubKey;
	return cPubKey.IsFullyValid();
}
