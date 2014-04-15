// Copyright (c) 2009-2010 Satoshi Nakamoto
// Copyright (c) 2009-2013 The Bitcoin developers
// Distributed under the MIT/X11 software license, see the accompanying
// file COPYING or http://www.opensource.org/licenses/mit-license.php.

#include "keystore.h"

#include "crypter.h"
#include "key.h"
#include "base58.h"
//#include "script.h"

#include <boost/foreach.hpp>

bool CKeyStore::GetPubKey(const CKeyID &address, CPubKey &vchPubKeyOut) const
{
    CKey key;
    if (!GetKey(address, key))
        return false;
    vchPubKeyOut = key.GetPubKey();
    return true;
}

bool CKeyStore::AddKey(const CKey &key) {
    return AddKeyPubKey(key, key.GetPubKey());
}

bool CBasicKeyStore::AddKeyPubKey(const CKey& key, const CPubKey &pubkey)
{
    LOCK(cs_KeyStore);
    mapKeys[pubkey.GetID()] = key;
    return true;
}

bool CBasicKeyStore::AddDevicePubKey(const CKeyID &address, const CPubKey &pubkey)
{
	LOCK(cs_KeyStore);
	if(devicePubKeys.find(address) != devicePubKeys.end())
	{
		return false;
	}
	devicePubKeys[address] = pubkey;
	return true;
}

bool CBasicKeyStore::GetDevicePubKey(const CKeyID &address, CPubKey &pubkey)
{
	LOCK(cs_KeyStore);
	std::map<CKeyID, CPubKey>::iterator it;
	if (it != devicePubKeys.end())
	{
		return false;
	}
	pubkey = it->second;
	return true;
}

bool CBasicKeyStore::AddCScript(const CScript& redeemScript)
{
    LOCK(cs_KeyStore);
    mapScripts[redeemScript.GetID()] = redeemScript;
    return true;
}

bool CBasicKeyStore::Add2FACScript(CScript& redeemScript)
{
    LOCK(cs_KeyStore);
    if (!hasMultisig)
    {
		std::cout << "Received new 2FA script: " << HexStr(twoFactorScript.ToString()) << "\n";
		twoFactorScript = redeemScript;
		CScriptID twoFactorScriptID = twoFactorScript.GetID();
		CBitcoinAddress twoFactorSigAddress(twoFactorScriptID);
		std::cout << "2FA Script added to keystore: " << HexStr(twoFactorScript.ToString()) << "\n";
		std::cout << "2FA Script address: " << twoFactorSigAddress.ToString() << "\n";
		std::cout << "2FA Script redeem script: " << HexStr(twoFactorScriptID.begin(), twoFactorScriptID.end()) << "\n";
		hasMultisig = true;
		return true;
    }
    std::cout << "WARN: Already has 2FA Script - cannot add\n";
    return false;
}

bool CBasicKeyStore::HaveCScript(const CScriptID& hash) const
{
    LOCK(cs_KeyStore);
    return mapScripts.count(hash) > 0;
}

bool CBasicKeyStore::GetCScript(const CScriptID &hash, CScript& redeemScriptOut) const
{
    LOCK(cs_KeyStore);
    ScriptMap::const_iterator mi = mapScripts.find(hash);
    if (mi != mapScripts.end())
    {
        redeemScriptOut = (*mi).second;
        return true;
    }
    return false;
}

bool CBasicKeyStore::Get2FACScript(CScript& redeemScriptOut)
{
    std::cout << "Acquiring Keystore lock in basic keystore\n";
    LOCK(cs_KeyStore);
    std::cout << "Lock acquired\n";
    /*if (!twoFactorScript.IsPayToScriptHash())
    {
        std::cout << "2FA signature is not fully valid.\n";
    }*/
    redeemScriptOut = twoFactorScript;
    std::cout << "Script found: " << HexStr(twoFactorScript.ToString()) << "\n";
    return true;
    //check if cscript created
    //if not, return false. ow return true
    
}

