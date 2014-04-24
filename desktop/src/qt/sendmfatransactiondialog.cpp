#include "sendmfatransactiondialog.h"
#include "ui_sendmfatransactiondialog.h"
#include "addresstablemodel.h"

#ifdef USE_QRCODE
#include <qrencode.h>
#include <QPixmap>
#endif

SendMFATransactionDialog::SendMFATransactionDialog(QWidget *parent) :
    QDialog(parent),
    ui(new Ui::SendMFATransactionDialog)
{
    ui->setupUi(this);
}

SendMFATransactionDialog::~SendMFATransactionDialog()
{
    delete ui;
}

void SendMFATransactionDialog::setQRCode(std::string qrCodeStr)
{

#ifdef USE_QRCODE
    ui->lblQRCode->setText("");
    QRcode *code = QRcode_encodeString(qrCodeStr.c_str(), 0, QR_ECLEVEL_L, QR_MODE_8, 1);
    if (!code)
    {
    	ui->lblQRCode->setText(tr("Error encoding txn hex into QR Code."));
        return;
    }
    QImage myImage = QImage(code->width + 8, code->width + 8, QImage::Format_RGB32);
    myImage.fill(0xffffff);
    unsigned char *p = code->data;
    for (int y = 0; y < code->width; y++)
    {
    	for (int x = 0; x < code->width; x++)
        {
    		myImage.setPixel(x + 4, y + 4, ((*p & 1) ? 0x0 : 0xffffff));
            p++;
        }
    }
    QRcode_free(code);

    ui->lblQRCode->setPixmap(QPixmap::fromImage(myImage).scaled(300, 300));
#else
    ui->lblQRCode->setText("Your computer does not support QR codes.");
#endif
}

void SendMFATransactionDialog::on_buttonBox_clicked()
{
    close();
}
