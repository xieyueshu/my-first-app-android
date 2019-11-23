package com.example.myfirstapp;

import com.google.gson.Gson;

import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jFactory;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.EthSendRawTransaction;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.EthTransaction;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import okhttp3.OkHttpClient;
import okhttp3.Response;

public class DemoWeb3j {
    /**
     * 获取钱包地址余额演示demo
     */
    public static void demoEthGetBalance() throws Exception {
        String address = "0x613C023F95f8DDB694AE43Ea989E9C82c0325D3A";
        String ethRpc = "https://mainnet.infura.io/llyrtzQ3YhkdESt2Fzrk";
        Web3j web3j = Web3jFactory.build(new HttpService(ethRpc));
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    EthGetBalance responseBalance = web3j.ethGetBalance(address, DefaultBlockParameterName.LATEST)
                            .send();
                    System.out.println("账户地址：" + address);
                    System.out.println("查询结果：" + new Gson().toJson(responseBalance));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 获取钱包地址交易数量演示demo
     */
    public static void demoEthGetTransactionCount() throws Exception {
        String address = "0x613C023F95f8DDB694AE43Ea989E9C82c0325D3A";
        String ethRpc = "https://mainnet.infura.io/llyrtzQ3YhkdESt2Fzrk";
        Web3j web3j = Web3jFactory.build(new HttpService(ethRpc));
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    EthGetTransactionCount response = web3j.ethGetTransactionCount(address, DefaultBlockParameterName.LATEST)
                            .send();
                    System.out.println("账户地址：" + address);
                    System.out.println("查询结果：" + new Gson().toJson(response));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 发起交易演示demo
     */
    public static void demoEthSendRawTransaction() throws Exception {
        String address = "0x613C023F95f8DDB694AE43Ea989E9C82c0325D3A";
        String ethRpc = "https://mainnet.infura.io/llyrtzQ3YhkdESt2Fzrk";
        String signData = "0xf86d018502540be4008302328094405a35e1444299943667d47b2bab7787cbeb61fd88016345785d8a0000801ca0961ef9784a087ccbd0bb61ccbdcd1ce214db1a045555f70b0ae6d1ad441a76faa07fe4c3cb63c730965a7a642c152f8b13b893a6c58f1cdf82f04af285043f180b";
        Web3j web3j = Web3jFactory.build(new HttpService(ethRpc));
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    EthSendTransaction response = web3j.ethSendRawTransaction(signData)
                            .send();
                    System.out.println("账户地址：" + address);
                    System.out.println("交易结果：" + new Gson().toJson(response));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 按照交易哈希获取交易详情演示demo
     */
    public static void demoEthGetTransactionByHash() throws Exception {
        String ethRpc = "https://mainnet.infura.io/llyrtzQ3YhkdESt2Fzrk";
        String hash = "0x8595abd24f1f8590681064e3718fd559db3f50e0342e75b3382a3ec1cc57ce25";
        Web3j web3j = Web3jFactory.build(new HttpService(ethRpc));
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    EthTransaction response = web3j.ethGetTransactionByHash(hash)
                            .send();
                    System.out.println("交易哈希：" + hash);
                    System.out.println("查询结果：" + new Gson().toJson(response));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 获取交易收据演示demo
     */
    public static void demoEthGetTransactionReceipt() throws Exception {
        String ethRpc = "https://mainnet.infura.io/llyrtzQ3YhkdESt2Fzrk";
        String hash = "0x8595abd24f1f8590681064e3718fd559db3f50e0342e75b3382a3ec1cc57ce25";
        Web3j web3j = Web3jFactory.build(new HttpService(ethRpc));
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    EthGetTransactionReceipt response = web3j.ethGetTransactionReceipt(hash)
                            .send();
                    System.out.println("交易哈希：" + hash);
                    System.out.println("查询结果：" + new Gson().toJson(response));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 合约创建演示demo
     */
    public static void demoContractCreateEthGetTransactionReceipt() throws Exception {
        String ethRpc = "https://mainnet.infura.io/llyrtzQ3YhkdESt2Fzrk";
        String address = "0x613C023F95f8DDB694AE43Ea989E9C82c0325D3A";
        String signData = "0xf90b518204bb850165a0bc00830dbba08080b90afc60606040526002805460ff19166012179055341561001c57600080fd5b604051610a1c380380610a1" +
                "c833981016040528080519190602001805182019190602001805160025460ff16600a0a85026003819055600160a060020a0333166000908152600460205260408120" +
                "9190915592019190508280516100849291602001906100a1565b5060018180516100989291602001906100a1565b5050505061013c565b828054600181600116156" +
                "101000203166002900490600052602060002090601f016020900481019282601f106100e257805160ff191683800117855561010f565b82800160010185558215610" +
                "10f579182015b8281111561010f5782518255916020019190600101906100f4565b5061011b92915061011f565b5090565b61013991905b8082111561011b576000" +
                "8155600101610125565b90565b6108d18061014b6000396000f3006060604052600436106100b95763ffffffff7c01000000000000000000000000000000000000" +
                "0000000000000000000060003504166306fdde0381146100be578063095ea7b31461014857806318160ddd1461017e57806323b872dd146101a3578063313ce567" +
                "146101cb57806342966c68146101f457806370a082311461020a57806379cc67901461022957806395d89b411461024b578063a9059cbb1461025e578063cae9c" +
                "a5114610282578063dd62ed3e146102e7575b600080fd5b34156100c957600080fd5b6100d161030c565b604051602080825281908101838181518152602001915" +
                "08051906020019080838360005b8381101561010d5780820151838201526020016100f5565b50505050905090810190601f16801561013a57808203805160018360" +
                "20036101000a031916815260200191505b509250505060405180910390f35b341561015357600080fd5b61016a600160a060020a03600435166024356103aa565b60" +
                "4051901515815260200160405180910390f35b341561018957600080fd5b6101916103da565b60405190815260200160405180910390f35b34156101ae57600080fd" +
                "5b61016a600160a060020a03600435811690602435166044356103e0565b34156101d657600080fd5b6101de610457565b60405160ff9091168152602001604051809" +
                "10390f35b34156101ff57600080fd5b61016a600435610460565b341561021557600080fd5b610191600160a060020a03600435166104eb565b341561023457600080" +
                "fd5b61016a600160a060020a03600435166024356104fd565b341561025657600080fd5b6100d16105d9565b341561026957600080fd5b610280600160a060020a036" +
                "0043516602435610644565b005b341561028d57600080fd5b61016a60048035600160a060020a03169060248035919060649060443590810190830135806020601f82" +
                "01819004810201604051908101604052818152929190602084018383808284375094965061065395505050505050565b34156102f257600080fd5b610191600160a060" +
                "020a0360043581169060243516610781565b60008054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818" +
                "152602001828054600181600116156101000203166002900480156103a25780601f10610377576101008083540402835291602001916103a2565b8201919060005260" +
                "20600020905b81548152906001019060200180831161038557829003601f168201915b505050505081565b600160a060020a03338116600090815260056020908152604" +
                "0808320938616835292905220819055600192915050565b60035481565b600160a060020a038084166000908152600560209081526040808320339094168352929052908" +
                "1205482111561041557600080fd5b600160a060020a038085166000908152600560209081526040808320339094168352929052208054839003905561044d84848461079" +
                "e565b5060019392505050565b60025460ff1681565b600160a060020a0333166000908152600460205260408120548290101561048657600080fd5b600160a060020a03" +
                "331660008181526004602052604090819020805485900390556003805485900390557fcc16f5dbb4873280815c1ee09dbd06736cffcc184412cf7a71a0fdb75d397ca59" +
                "084905190815260200160405180910390a2506001919050565b60046020526000908152604090205481565b600160a060020a0382166000908152600460205260408120" +
                "548290101561052357600080fd5b600160a060020a038084166000908152600560209081526040808320339094168352929052205482111561055657600080fd5b600160" +
                "a060020a03808416600081815260046020908152604080832080548890039055600582528083203390951683529390528290208054859003905560038054859003905590" +
                "7fcc16f5dbb4873280815c1ee09dbd06736cffcc184412cf7a71a0fdb75d397ca59084905190815260200160405180910390a250600192915050565b6001805460018160" +
                "0116156101000203166002900480601f01602080910402602001604051908101604052809291908181526020018280546001816001161561010002031660029004801561" +
                "03a25780601f10610377576101008083540402835291602001916103a2565b61064f33838361079e565b5050565b60008361066081856103aa565b156107795780600160" +
                "a060020a0316638f4ffcb1338630876040518563ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018085600160a060" +
                "020a0316600160a060020a0316815260200184815260200183600160a060020a0316600160a060020a031681526020018060200182810382528381815181526020019150" +
                "8051906020019080838360005b838110156107165780820151838201526020016106fe565b50505050905090810190601f16801561074357808203805160018360200361" +
                "01000a031916815260200191505b5095505050505050600060405180830381600087803b151561076457600080fd5b5af1151561077157600080fd5b505050600191505b" +
                "509392505050565b600560209081526000928352604080842090915290825290205481565b6000600160a060020a03831615156107b557600080fd5b600160a060020a0384" +
                "16600090815260046020526040902054829010156107db57600080fd5b600160a060020a038316600090815260046020526040902054828101101561080257600080fd5b50" +
                "600160a060020a0380831660008181526004602052604080822080549488168084528284208054888103909155938590528154870190915591909301927fddf252ad1be2c89" +
                "b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef9085905190815260200160405180910390a3600160a060020a03808416600090815260046020526040808220549" +
                "28716825290205401811461089f57fe5b505050505600a165627a7a72305820716da1fe3419571a3c3fa845a628d9f1ec18c456f298161c22d2fbe6b6ea70260029000000000" +
                "00000000000000000000000000000000000000000000002540be4000000000000000000000000000000000000000000000000000000000000000060000000000000000000000" +
                "00000000000000000000000000000000000000000a0000000000000000000000000000000000000000000000000000000000000000c4269742042697420436f696e000000000" +
                "00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000003424243000000000000000000000000000000000000000" +
                "00000000000000000001ca0eb1025398d207eac0345de22908e6a7114955b5a395e679591d67df4af17dfeca03ad28bb50847bf740ea65693d73428938e55d77e157644e534deb35700ae2b20";
        Web3j web3j = Web3jFactory.build(new HttpService(ethRpc));
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    EthSendTransaction response = web3j.ethSendRawTransaction(signData)
                            .send();
                    System.out.println("账户地址：" + address);
                    System.out.println("交易结果：" + new Gson().toJson(response));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    /**
     * 获取合约收据演示demo
     */
    public static void demoContractEthGetTransactionReceipt() throws Exception {
        String ethRpc = "https://mainnet.infura.io/llyrtzQ3YhkdESt2Fzrk";
        String hash = "0x75ca0ef5c43c8556d2cd77aa3aa0da6946a2e4e5ce4a62a86c7e24a9e28c55a5";
        Web3j web3j = Web3jFactory.build(new HttpService(ethRpc));
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    EthGetTransactionReceipt response = web3j.ethGetTransactionReceipt(hash)
                            .send();
                    System.out.println("交易哈希：" + hash);
                    System.out.println("查询结果：" + new Gson().toJson(response));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 获取代币余额演示demo
     */
    public static void getEthCall() throws Exception {
        String ethRpc = "https://mainnet.infura.io/llyrtzQ3YhkdESt2Fzrk";
        String address = "0x030e37ddd7df1b43db172b23916d523f1599c6cb";
        String contractAddress = "0xB8c77482e45F1F44dE1745F52C74426C631bDD52";
        Web3j web3j = Web3jFactory.build(new HttpService(ethRpc));
        org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                "balanceOf",
                Collections.singletonList(new Address(address)),
                Collections.singletonList(new TypeReference<Uint256>() {
                }));
        org.web3j.protocol.core.methods.response.EthCall response = web3j.ethCall(
                Transaction.createEthCallTransaction(address, contractAddress, FunctionEncoder.encode(function)),
                DefaultBlockParameterName.LATEST)
                .sendAsync().get();

        System.out.println("合约地址：" + contractAddress);
        System.out.println("查询结果：" + new Gson().toJson(response));
    }

    /**
     * 代币转账交易演示demo
     */
    public static void demoContractEthSendRawTransaction() throws Exception {
        String contractAddress = "0xB8c77482e45F1F44dE1745F52C74426C631bDD52";
        String ethRpc = "https://mainnet.infura.io/llyrtzQ3YhkdESt2Fzrk";
        String signData = "0xf8ac8204bc850165a0bc0083013880945949f052e5f26f5822ebc0c48b795b98a465cf5880b844a9059cbb000000000000000000000000405a35e1444299943667d47b2bab7787cbeb61fd00000000000000000000000000000000000000000000003635c9adc5dea000001ba0f19020f5df4cfd612a256021e99388a22d6b8b50d58584fc5652c789a621da2aa048275196184c67f4206b94b348ee52a4b9b8d89054586e329b5754ba78973f8a";
        Web3j web3j = Web3jFactory.build(new HttpService(ethRpc));
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    EthSendTransaction response = web3j.ethSendRawTransaction(signData)
                            .send();
                    System.out.println("合约地址：" + contractAddress);
                    System.out.println("交易结果：" + new Gson().toJson(response));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 代币日志演示demo
     */
    public static void demoContract() throws Exception {
        String ethRpc = "https://mainnet.infura.io/llyrtzQ3YhkdESt2Fzrk";
        String hash = "0x30459fc2ee1bc4adf2ba26ca1a0ca78f4e0cbe14195af76352e9f84aa4866085";
        Web3j web3j = Web3jFactory.build(new HttpService(ethRpc));
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    EthGetTransactionReceipt response = web3j.ethGetTransactionReceipt(hash)
                            .send();
                    System.out.println("交易哈希：" + hash);
                    System.out.println("查询结果：" + new Gson().toJson(response));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 获取代币发行量演示demo
     */
    public static void getContractEthCall() throws Exception {
        String ethRpc = "https://mainnet.infura.io/llyrtzQ3YhkdESt2Fzrk";
        String emptyAddress = "0x0000000000000000000000000000000000000000";
        String contractAddress = "0xB8c77482e45F1F44dE1745F52C74426C631bDD52";
        Web3j web3j = Web3jFactory.build(new HttpService(ethRpc));
        org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                "totalSupply",
                new ArrayList<>(),
                Collections.singletonList(new TypeReference<Uint256>() {
                }));
        org.web3j.protocol.core.methods.response.EthCall response = web3j.ethCall(
                Transaction.createEthCallTransaction(emptyAddress, contractAddress, FunctionEncoder.encode(function)),
                DefaultBlockParameterName.LATEST)
                .sendAsync().get();

        System.out.println("合约地址：" + contractAddress);
        System.out.println("查询结果：" + new Gson().toJson(response));
    }

    /**
     * 区块高度演示demo
     */
    public static void demoEthBlockNumber() throws Exception {
        String ethRpc = "https://mainnet.infura.io/llyrtzQ3YhkdESt2Fzrk";
        Web3j web3j = Web3jFactory.build(new HttpService(ethRpc));
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    EthBlockNumber response = web3j.ethBlockNumber()
                            .send();
                    System.out.println("查询结果：" + new Gson().toJson(response));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 区块ByNumber演示demo
     */
    public static void demoEthGetBlockByNumber() throws Exception {
        String ethRpc = "https://mainnet.infura.io/llyrtzQ3YhkdESt2Fzrk";
        Web3j web3j = Web3jFactory.build(new HttpService(ethRpc));
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    EthBlock response = web3j.ethGetBlockByNumber(DefaultBlockParameterName.LATEST,false)
                            .send();
                    System.out.println("查询结果：" + new Gson().toJson(response));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
