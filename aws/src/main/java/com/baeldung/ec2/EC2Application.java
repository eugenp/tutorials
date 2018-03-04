package com.baeldung.ec2;

import java.util.Arrays;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.AuthorizeSecurityGroupIngressRequest;
import com.amazonaws.services.ec2.model.CreateKeyPairRequest;
import com.amazonaws.services.ec2.model.CreateKeyPairResult;
import com.amazonaws.services.ec2.model.CreateSecurityGroupRequest;
import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.DescribeKeyPairsRequest;
import com.amazonaws.services.ec2.model.DescribeKeyPairsResult;
import com.amazonaws.services.ec2.model.IpPermission;
import com.amazonaws.services.ec2.model.IpRange;
import com.amazonaws.services.ec2.model.MonitorInstancesRequest;
import com.amazonaws.services.ec2.model.RebootInstancesRequest;
import com.amazonaws.services.ec2.model.RunInstancesRequest;
import com.amazonaws.services.ec2.model.StartInstancesRequest;
import com.amazonaws.services.ec2.model.StopInstancesRequest;
import com.amazonaws.services.ec2.model.UnmonitorInstancesRequest;

public class EC2Application {

    private static final AWSCredentials credentials;

    static {
        // put your accesskey and secretkey here
        credentials = new BasicAWSCredentials(
            "<AWS accesskey>", 
            "<AWS secretkey>"
        );
    }

    public static void main(String[] args) {

        String yourInstanceId = "<you-instance>";
        
        // 0) - Set up the client
        AmazonEC2 ec2Client = AmazonEC2ClientBuilder.standard()
            .withCredentials(new AWSStaticCredentialsProvider(credentials))
            .withRegion(Regions.US_EAST_1)
            .build();

        // 1) - Create a security group
        CreateSecurityGroupRequest createSecurityGroupRequest = new CreateSecurityGroupRequest().withGroupName("BaeldungSecurityGroup")
            .withDescription("Baeldung Security Group");
        ec2Client.createSecurityGroup(createSecurityGroupRequest);

        // 2) - Allow HTTP and SSH traffic
        IpRange ipRange1 = new IpRange().withCidrIp("0.0.0.0/0");

        IpPermission ipPermission1 = new IpPermission().withIpv4Ranges(Arrays.asList(new IpRange[] { ipRange1 }))
            .withIpProtocol("tcp")
            .withFromPort(80)
            .withToPort(80);

        IpPermission ipPermission2 = new IpPermission().withIpv4Ranges(Arrays.asList(new IpRange[] { ipRange1 }))
            .withIpProtocol("tcp")
            .withFromPort(22)
            .withToPort(22);

        AuthorizeSecurityGroupIngressRequest authorizeSecurityGroupIngressRequest = new AuthorizeSecurityGroupIngressRequest()
            .withGroupName("BaeldungSecurityGroup")
            .withIpPermissions(ipPermission1, ipPermission2);

        ec2Client.authorizeSecurityGroupIngress(authorizeSecurityGroupIngressRequest);

        // 3) - Create KeyPair
        CreateKeyPairRequest createKeyPairRequest = new CreateKeyPairRequest()
            .withKeyName("baeldung-key-pair");
        CreateKeyPairResult createKeyPairResult = ec2Client.createKeyPair(createKeyPairRequest);
        String privateKey = createKeyPairResult
            .getKeyPair()
            .getKeyMaterial(); // make sure you keep it, the private key, Amazon doesn't store the private key

        // 4) - See what key-pairs you've got
        DescribeKeyPairsRequest describeKeyPairsRequest = new DescribeKeyPairsRequest();
        DescribeKeyPairsResult describeKeyPairsResult = ec2Client.describeKeyPairs(describeKeyPairsRequest);

        // 5) - Launch an Amazon Instance
        RunInstancesRequest runInstancesRequest = new RunInstancesRequest().withImageId("ami-97785bed") // https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/AMIs.html | https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/usingsharedamis-finding.html
            .withInstanceType("t2.micro") // https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/instance-types.html
            .withMinCount(1)
            .withMaxCount(1)
            .withKeyName("baeldung-key-pair") // optional - if not present, can't connect to instance
            .withSecurityGroups("BaeldungSecurityGroup");

        ec2Client.runInstances(runInstancesRequest);

        // 6) Monitor Instances
        MonitorInstancesRequest monitorInstancesRequest = new MonitorInstancesRequest()
            .withInstanceIds(yourInstanceId);

        ec2Client.monitorInstances(monitorInstancesRequest);

        UnmonitorInstancesRequest unmonitorInstancesRequest = new UnmonitorInstancesRequest()
            .withInstanceIds(yourInstanceId);

        ec2Client.unmonitorInstances(unmonitorInstancesRequest);

        // 7) - Reboot an Instance

        RebootInstancesRequest rebootInstancesRequest = new RebootInstancesRequest()
            .withInstanceIds(yourInstanceId);

        ec2Client.rebootInstances(rebootInstancesRequest);

        // 8) - Stop an Instance
        StopInstancesRequest stopInstancesRequest = new StopInstancesRequest()
            .withInstanceIds(yourInstanceId);

        ec2Client.stopInstances(stopInstancesRequest)
            .getStoppingInstances()
            .get(0)
            .getPreviousState()
            .getName();

        // 9) - Start an Instance
        StartInstancesRequest startInstancesRequest = new StartInstancesRequest()
            .withInstanceIds("instance-id");

        ec2Client.startInstances(startInstancesRequest);

        // 10) - Describe an Instance
        DescribeInstancesRequest describeInstancesRequest = new DescribeInstancesRequest();
        DescribeInstancesResult response = ec2Client.describeInstances(describeInstancesRequest);
        System.out.println(response.getReservations()
            .get(0)
            .getInstances()
            .get(0)
            .getKernelId());
    }
}
