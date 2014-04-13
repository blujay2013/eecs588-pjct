#include "askdevicepublickey.h"
#include "ui_askdevicepublickey.h"
#include "addresstablemodel.h"
AskDevicePublicKey::AskDevicePublicKey(AddressTableModel *model,QWidget *parent) :
    QDialog(parent),
    model(model),
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

	if (cPubKeyBuf.IsFullyValid())
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

    //CPubKey cPub3KeyBuf(ParseHex(pubKey));
    //model is addresstablemodel
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
