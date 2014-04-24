#include "askdevicepublickey.h"
#include "ui_askdevicepublickey.h"
#include "addresstablemodel.h"
AskDevicePublicKey::AskDevicePublicKey(AddressTableModel *model,QWidget *parent) :
    QDialog(parent),
    model(model),
    ui(new Ui::AskDevicePublicKey)
{
    ui->setupUi(this);
    ui->buttonBox->setVisible(false);
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
		std::vector<unsigned char> pubKeyData(pubKey.begin(), pubKey.end());
		CPubKey cPubKeyBuf(ParseHex(pubKey));

		if (cPubKeyBuf.IsFullyValid())
		{
			ui->buttonBox->setVisible(true);
		} else
		{
			ui->buttonBox->setVisible(false);
		}
    }
}

void AskDevicePublicKey::on_buttonBox_clicked()
{
    model->addRow(AddressTableModel::DeviceTwoFactor,
		  "test label",
		  ui->devicePubKeyBox->text());
    close();
}

bool AskDevicePublicKey::getPubKey(CPubKey &myCPubKey)
{
    myCPubKey = cPubKey;
    return cPubKey.IsFullyValid();
}
