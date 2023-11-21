
<details>
	<summary>What is gRPC :</summary> 
RPC is an open-source remote procedure call framework created by Google in 2016, which enables one machine to invoke code on another machine as if it is a local function call. It is popular due to its thriving developer ecosystem and its high performance out of the box. more here: https://www.youtube.com/watch?v=gnchfOojMk4
 
#### 🌐 gRPC is an open-source remote procedure call framework created by Google in 2016.
#### 🚀 gRPC has a thriving developer ecosystem and makes it easy to develop production-quality and type-safe APIs.
#### 🔄 gRPC uses Protocol Buffers as its data interchange format, which provides several advantages and supports strongly typed schema definitions.
#### 📚 Protocol Buffers provide broad tooling support to turn the schema defined in the proto file into data access classes for all popular programming languages.
#### ⚡ gRPC is high-performance due to Protocol Buffers' efficient binary encoding format and its use of HTTP/2 streams. 
#### 👥 gRPC is the preferred inter-service communication mechanism between microservices in data centers and is increasingly used in native mobile clients.
#### ❌ gRPC currently has limited support in web browsers, but it is possible to make gRPC calls from a browser with the help of a proxy technology called gRPC-Web.

</details>

<details>
	<summary>gRPC Resource </summary>

### What is RPC? gRPC Introduction : https://www.youtube.com/watch?v=gnchfOojMk4
### gRPC vs REST Performance Comparison : https://www.vinsguru.com/grpc-vs-rest-performance-comparison/
### Basic tutorial : https://www.youtube.com/watch?v=2CWYorTWyGs&t=488s
### udemy tutorial_ vinsguru : https://drive.google.com/drive/folders/1GkjRukgV5hn2glM4Bq6QzhrunjZ9YINE?usp=sharing 
### (Udemy) vinsguru code example : https://github.com/vinsguru/vinsguru-blog-code-samples/tree/master/grpc
### gRPC Spring Boot Integration  Articale : https://www.vinsguru.com/grpc-spring-boot-integration/

###  gRPC file upload : https://www.vinsguru.com/grpc-file-upload-client-streaming/
### grpc-spring-boot-starter : https://github.com/yidongnan/grpc-spring-boot-starter/tree/master
### https://github.com/Sayem-Hasnat/gRPC-SpringBoot-Practise/tree/dev
### bloomrpc (grpc api tester) : https://github.com/bloomrpc/bloomrpc/releases
</details>


# gRPC with springboot microservice Setup
first have to create a  maven module project 
<details>
	<summary>proto-module</summary>

It will contains the protocol buffers file (.proto) where request , 
response and fields will be written in Protobuf language .
Step 01 : Add Dependencies on pom.xml file

````ruby
  <name>message-common</name>
    <properties>
        <protobuf.version>3.14.0</protobuf.version>
        <protobuf-plugin.version>0.6.1</protobuf-plugin.version>
        <grpc.version>1.35.0</grpc.version>
        <java.version>11</java.version>
        <maven.compiler.source>11</maven.compiler.source>
        <maven.compiler.target>11</maven.compiler.target>
    </properties>

    <dependencies>
        <dependency>
            <groupId>io.grpc</groupId>
            <artifactId>grpc-stub</artifactId>
            <version>1.35.0</version>
        </dependency>
        <dependency>
            <groupId>io.grpc</groupId>
            <artifactId>grpc-protobuf</artifactId>
            <version>1.35.0</version>
        </dependency>
        <dependency>
            <!-- Java 9+ compatibility - Do NOT update to 2.0.0 -->
            <groupId>jakarta.annotation</groupId>
            <artifactId>jakarta.annotation-api</artifactId>
            <version>1.3.5</version>
            <optional>true</optional>
        </dependency>
    </dependencies>
<plugins>
            <plugin>
                <groupId>org.xolstice.maven.plugins</groupId>
                <artifactId>protobuf-maven-plugin</artifactId>
                <version>0.6.1</version>
                <configuration>
                    <protocArtifact>com.google.protobuf:protoc:${protobuf.version}:exe:linux-x86_64</protocArtifact>
                    <pluginId>grpc-java</pluginId>
                    <pluginArtifact>io.grpc:protoc-gen-grpc-java:${grpc.version}:exe:linux-x86_64</pluginArtifact>
                </configuration>
                <executions>
                    <execution>
                        <goals>
                            <goal>compile</goal>
                            <goal>compile-custom</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
````
</details>

<details>
	<summary>Step 02: Add service.proto in src/main/proto package file </summary>

````ruby
  syntax = "proto3";

  option java_multiple_files = true;
  option java_package = "com.hasnat.proto.bankservice";

  // client will request by account number on this format to get Balance
  message BalanceRequest{
    int32 account_number = 1;
  }

  //server will response this
  message Balance {
    int32  amount = 1;
    int32 account_number = 2;
  }
 // client will request
  message WithdrawRequest{
    int32 account_number = 1;
    int32  requested_amount = 2;
      }

 //server will response this
     message Money{
    int32 withdrawal_money = 1;
    int32 availableBalance = 2;
      }

  service  BankService{
    //unary service
    rpc getBalance(BalanceRequest) returns (Balance);

    //server side streaming service
    rpc withdraw(WithdrawRequest) returns (stream Money);

  }

````

##	After add the proto file this proto-module need to clean build and add the module on project modules where grpc server and client service .

</details>

<details>
	<summary>Step 03 : Add proto-module in the pom.xml of other microservices </summary>
	
````ruby
<!--greeting message module-->
		<dependency>
			<groupId>com.hasnat</groupId>
			<artifactId>message-common</artifactId>
			<version>0.0.1-SNAPSHOT</version>
		</dependency>

````


</details>

<details>
	<summary>Step 04 : Add grpc client & grpc service dependencies </summary>
	
 ````ruby

<dependency>
                        <!-- gRPC client dependency -->
			<groupId>net.devh</groupId>
			<artifactId>grpc-client-spring-boot-starter</artifactId>
			<version>2.12.0.RELEASE</version>
		</dependency>
		<!-- gRPC server dependency -->
		<dependency>
			<groupId>net.devh</groupId>
			<artifactId>grpc-server-spring-boot-starter</artifactId>
			<version>2.12.0.RELEASE</version>
</dependency>
````
</details>

## gRPC server module Setup
<details>
	<summary>Step 01 : Add configuration in application.properties </summary>


````ruby

spring.application.name=grpc-server-local
grpc.server.port=9898
server.port=8083

grpc.client.grpc-client-local.address=static://127.0.0.1:8085
grpc.client.grpc-client-local.enableKeepAlive=true
grpc.client.grpc-client-local.keepAliveWithoutCalls=true
#grpc.client.grpc-server-local.negotiationType=plaintext
grpc.client.grpc-client-local.negotiationType=PLAINTEXT

````
</details>

<details>
	<summary>Step 02 : Add grpc-client service in service class </summary>
	
````ruby
@GrpcService
public class BankService extends BankServiceGrpc.BankServiceImplBase {

    //Unary RPC
    @Override
    public void getBalance(BalanceRequest balanceRequest, StreamObserver<Balance> responseObserver) {
        int accountNumber = balanceRequest.getAccountNumber();
        Balance balance = Balance.newBuilder()
                .setAmount(BankAccountDB.getBalance(accountNumber))
                .setAccountNumber(accountNumber)
                .build();

        BankAccountDB.printAccountDetails();
        responseObserver.onNext(balance);
        responseObserver.onCompleted();
    }

    //Client-streaming RPC
    @Override
    public void withdraw(WithdrawRequest request, StreamObserver<Money> responseObserver) {
        int accountNumber = request.getAccountNumber();
        int withdrawalMoney = request.getRequestedAmount();
        int balance = BankAccountDB.getBalance(accountNumber);


        // gRPC error response Handling
        if (balance<withdrawalMoney){
            Status status = Status.FAILED_PRECONDITION
                    .withDescription("Not enough money, account have: "+ balance);
            responseObserver.onError(status.asRuntimeException());
            return;
        }
        for (int i = 0; i < (withdrawalMoney / 10); i++) {
              BankAccountDB.deductBalance(accountNumber, 10);
            Money money  = Money.newBuilder()
                    .setWithdrawalMoney(withdrawalMoney)
                    .setAvailableBalance(BankAccountDB.getBalance(request.getAccountNumber()))
                    .build();
            responseObserver.onNext(money);

        }
        responseObserver.onCompleted();
    }
}

````

</details>

## gRPC client module Setup

<details>
	<summary>Step 01 : Add configuration in application.properties </summary>

 ````ruby

spring.application.name=grpc-client-local
server.port=8081
grpc.server.port=8085


grpc.client.grpc-server-local.address=static://127.0.0.1:9898
grpc.client.grpc-server-local.enableKeepAlive=true
grpc.client.grpc-server-local.keepAliveWithoutCalls=true
#grpc.client.grpc-server-local.negotiationType=plaintext
grpc.client.grpc-server-local.negotiationType=PLAINTEXT

````

</details>


<details>
	<summary>Step 02 : Create Controller for grpc-client service   </summary>
	
````ruby

@RestController
@RequestMapping("/")
public class BankServiceController {
    @Autowired
    private BankService bankService;

    @GetMapping(value = "client/{accountNumber}")
    public BalanceResponse getBalance(@PathVariable int accountNumber) {
        return bankService.getBalance(accountNumber);
    }

    @PostMapping(value = "client/withdraw")
    public void withdrawRequest(@RequestBody ClientWithdrawRequest withdrawRequest) {
         bankService.withdrawRequest(withdrawRequest);
    }
}

````

</details>


<details>
	<summary>Step 03 : Add grpc-client service in service class   </summary>


````ruby
@Service
public class BankService {
    @GrpcClient("grpc-server-local")
    private BankServiceGrpc.BankServiceBlockingStub bankServiceBlockingStub; 
// blockingStub used for Uniray call
    @GrpcClient("grpc-server-local")
    private BankServiceGrpc.BankServiceStub bankServiceStub; // Stub used for async streaming call

    //check unary RPC
    public BalanceResponse getBalance(int accountNumber) {
        BalanceRequest balanceRequest = BalanceRequest.newBuilder()
                .setAccountNumber(accountNumber)
                .build();
        final Balance balance = this.bankServiceBlockingStub.getBalance(balanceRequest);
        System.out.println("balance" + balance);
        BalanceResponse balanceResponse = new BalanceResponse(balance.getAccountNumber(),balance.getAmount());
        return balanceResponse;
    }

    public void withdrawRequest(ClientWithdrawRequest clientWithdrawRequest){

        WithdrawRequest withdrawRequestToServer =
                WithdrawRequest.newBuilder()
                        .setAccountNumber(clientWithdrawRequest.getAccountNumber())
                        .setRequestedAmount(clientWithdrawRequest.getAmount())
                        .build();
        this.bankServiceBlockingStub.withdraw(withdrawRequestToServer)
                .forEachRemaining(m -> System.out.println(m + "localTime "+ LocalTime.now()));
        CountDownLatch latch = new CountDownLatch(1);
        this.bankServiceStub.withdraw(withdrawRequestToServer, new MoneyStreamObserver());
    }
}
````
</details>







