package com.baeldung.ec2;

import java.util.Arrays;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.AuthorizeSecurityGroupIngressRequest;
import software.amazon.awssdk.services.ec2.model.CreateKeyPairRequest;
import software.amazon.awssdk.services.ec2.model.CreateKeyPairResponse;
import software.amazon.awssdk.services.ec2.model.CreateSecurityGroupRequest;
import software.amazon.awssdk.services.ec2.model.DescribeInstancesRequest;
import software.amazon.awssdk.services.ec2.model.DescribeInstancesResponse;
import software.amazon.awssdk.services.ec2.model.DescribeKeyPairsRequest;
import software.amazon.awssdk.services.ec2.model.DescribeKeyPairsResponse;
import software.amazon.awssdk.services.ec2.model.IpPermission;
import software.amazon.awssdk.services.ec2.model.IpRange;
import software.amazon.awssdk.services.ec2.model.MonitorInstancesRequest;
import software.amazon.awssdk.services.ec2.model.RebootInstancesRequest;
import software.amazon.awssdk.services.ec2.model.RunInstancesRequest;
import software.amazon.awssdk.services.ec2.model.RunInstancesResponse;
import software.amazon.awssdk.services.ec2.model.StartInstancesRequest;
import software.amazon.awssdk.services.ec2.model.StartInstancesResponse;
import software.amazon.awssdk.services.ec2.model.StopInstancesRequest;
import software.amazon.awssdk.services.ec2.model.UnmonitorInstancesRequest;

public class EC2Application {

    public static void main(String[] args) {
       
        // Set up the client
        Ec2Client ec2Client = Ec2Client.builder()
            .credentialsProvider(ProfileCredentialsProvider.create("default"))
            .region(Region.US_EAST_1)
            .build();

        // Create a security group
        CreateSecurityGroupRequest createSecurityGroupRequest = CreateSecurityGroupRequest.builder()
            .groupName("BaeldungSecurityGroup")
            .description("Baeldung Security Group")
            .build();

        ec2Client.createSecurityGroup(createSecurityGroupRequest);

        // Allow HTTP and SSH traffic
        IpRange ipRange1 = IpRange.builder()
            .cidrIp("0.0.0.0/0")
            .build();

        IpPermission ipPermission1 = IpPermission.builder()
            .ipRanges(Arrays.asList(ipRange1))
            .ipProtocol("tcp")
            .fromPort(80)
            .toPort(80)
            .build();

        IpPermission ipPermission2 = IpPermission.builder()
            .ipRanges(Arrays.asList(ipRange1))
            .ipProtocol("tcp")
            .fromPort(22)
            .toPort(22)
            .build();

        AuthorizeSecurityGroupIngressRequest authorizeSecurityGroupIngressRequest = AuthorizeSecurityGroupIngressRequest
            .builder()
            .groupName("BaeldungSecurityGroup")
            .ipPermissions(ipPermission1, ipPermission2)
            .build();

        ec2Client.authorizeSecurityGroupIngress(authorizeSecurityGroupIngressRequest);

        // Create KeyPair
        CreateKeyPairRequest createKeyPairRequest = CreateKeyPairRequest.builder()
            .keyName("baeldung-key-pair")
            .build();

        CreateKeyPairResponse createKeyPairResponse = ec2Client.createKeyPair(createKeyPairRequest);
        String privateKey = createKeyPairResponse.keyMaterial();
        // make sure you keep it, the private key, Amazon doesn't store the private key

        // See what key-pairs you've got
        DescribeKeyPairsRequest describeKeyPairsRequest = DescribeKeyPairsRequest.builder()
            .build();
        DescribeKeyPairsResponse describeKeyPairsResponse = ec2Client.describeKeyPairs(describeKeyPairsRequest);

        // Launch an Amazon Instance
        RunInstancesRequest runInstancesRequest = RunInstancesRequest.builder()
            .imageId("ami-97785bed")
            .instanceType("t2.micro") // https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/instance-types.html
            .minCount(1)
            .maxCount(1)
            .keyName("baeldung-key-pair") // optional - if not present, can't connect to instance
            .securityGroups("BaeldungSecurityGroup")
            .build();

        RunInstancesResponse runInstancesResponse = ec2Client.runInstances(runInstancesRequest);
        String yourInstanceId = runInstancesResponse.instances().get(0).instanceId();

        // Start an Instance
        StartInstancesRequest startInstancesRequest = StartInstancesRequest.builder()
            .instanceIds(yourInstanceId)
            .build();

        StartInstancesResponse startInstancesResponse = ec2Client.startInstances(startInstancesRequest);


        // Monitor Instances
        MonitorInstancesRequest monitorInstancesRequest = MonitorInstancesRequest.builder()
            .instanceIds(yourInstanceId)
            .build();


        ec2Client.monitorInstances(monitorInstancesRequest);

        UnmonitorInstancesRequest unmonitorInstancesRequest = UnmonitorInstancesRequest.builder()
            .instanceIds(yourInstanceId)
            .build();

        ec2Client.unmonitorInstances(unmonitorInstancesRequest);

        // Reboot an Instance
        RebootInstancesRequest rebootInstancesRequest = RebootInstancesRequest.builder()
            .instanceIds(yourInstanceId)
            .build();

        ec2Client.rebootInstances(rebootInstancesRequest);

        // Stop an Instance
        StopInstancesRequest stopInstancesRequest = StopInstancesRequest.builder()
            .instanceIds(yourInstanceId)
            .build();


        ec2Client.stopInstances(stopInstancesRequest)
            .stoppingInstances()
            .get(0)
            .previousState()
            .name();

        // Describe an Instance
        DescribeInstancesRequest describeInstancesRequest = DescribeInstancesRequest.builder().build();
        DescribeInstancesResponse response = ec2Client.describeInstances(describeInstancesRequest);
        System.out.println(response.reservations()
            .get(0)
            .instances()
            .get(0)
            .kernelId());
    }
}
