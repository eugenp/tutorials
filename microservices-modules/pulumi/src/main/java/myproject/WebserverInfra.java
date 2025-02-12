package myproject;

import com.pulumi.Context;
import com.pulumi.Pulumi;
import com.pulumi.aws.ec2.Instance;
import com.pulumi.aws.ec2.InstanceArgs;
import com.pulumi.aws.ec2.InternetGateway;
import com.pulumi.aws.ec2.InternetGatewayArgs;
import com.pulumi.aws.ec2.RouteTable;
import com.pulumi.aws.ec2.RouteTableArgs;
import com.pulumi.aws.ec2.RouteTableAssociation;
import com.pulumi.aws.ec2.RouteTableAssociationArgs;
import com.pulumi.aws.ec2.SecurityGroup;
import com.pulumi.aws.ec2.SecurityGroupArgs;
import com.pulumi.aws.ec2.Subnet;
import com.pulumi.aws.ec2.SubnetArgs;
import com.pulumi.aws.ec2.Vpc;
import com.pulumi.aws.ec2.VpcArgs;
import com.pulumi.aws.ec2.enums.InstanceType;
import com.pulumi.aws.ec2.inputs.RouteTableRouteArgs;
import com.pulumi.aws.s3.Bucket;
import com.pulumi.aws.s3.BucketArgs;
import com.pulumi.aws.vpc.SecurityGroupEgressRule;
import com.pulumi.aws.vpc.SecurityGroupEgressRuleArgs;
import com.pulumi.aws.vpc.SecurityGroupIngressRule;
import com.pulumi.aws.vpc.SecurityGroupIngressRuleArgs;
import com.pulumi.core.Output;

public class WebserverInfra {

    public static void main(String[] args) {
        Pulumi.run(WebserverInfra::stack);
    }

    public static void stack(Context ctx) {

        // Create an S3 bucket
        var bucket = new Bucket("pulumi-bucket", BucketArgs.builder()
          .build());

        // Create a VPC
        var vpc = new Vpc("vpc", VpcArgs.builder()
          .cidrBlock("10.0.0.0/16")
          .build());

        // Create an internet gateway.
        var gateway = new InternetGateway("gateway", InternetGatewayArgs.builder()
          .vpcId(vpc.id())
          .build());

        // Create a subnet that automatically assigns new instances a public IP address.
        var subnet = new Subnet("subnet", SubnetArgs.builder()
          .vpcId(vpc.id())
          .cidrBlock("10.0.1.0/24")
          .mapPublicIpOnLaunch(true)
          .build());

        // Create a route table.
        var routes = new RouteTable("routes", RouteTableArgs.builder()
          .vpcId(vpc.id())
          .routes(RouteTableRouteArgs.builder()
            .cidrBlock("0.0.0.0/0")
            .gatewayId(gateway.id())
            .build())
          .build());

        var routeTableAssociation = new RouteTableAssociation("route-table-association", RouteTableAssociationArgs.builder()
          .subnetId(subnet.id())
          .routeTableId(routes.id())
          .build());

        // Create a security group
        var securityGroup = new SecurityGroup("security-group", SecurityGroupArgs.builder()
          .name("allow_public")
          .vpcId(vpc.id())
          .build());

        var allowPort80Ipv4 = new SecurityGroupIngressRule("allowPort80Ingress", SecurityGroupIngressRuleArgs.builder()
          .securityGroupId(securityGroup.id())
          .cidrIpv4("0.0.0.0/0")
          .fromPort(80)
          .ipProtocol("tcp")
          .toPort(80)
          .build());

        var allowAllTrafficIpv4 = new SecurityGroupEgressRule("allowAllTrafficEgress", SecurityGroupEgressRuleArgs.builder()
          .securityGroupId(securityGroup.id())
          .cidrIpv4("0.0.0.0/0")
          .ipProtocol("-1")
          .build());

        // Define user data for the EC2 instance
        String userData = """
          #!/bin/bash
          yum update -y
          yum install -y httpd
          systemctl start httpd
          systemctl enable httpd
          echo "<h1>Hello World, welcome to our Pulumi deployed infrastructure.</h1>" > /var/www/html/index.html
          """;

        // Create an EC2 instance
        var instance = new Instance("my-instance", InstanceArgs.builder()
          .instanceType(InstanceType.T2_Micro) // Use a t2.micro instance
          .ami("ami-0e320147c22e46f12") // Amazon Linux 2 AMI
          .subnetId(subnet.id())
          .vpcSecurityGroupIds(Output.all(securityGroup.id()))
          .associatePublicIpAddress(true)
          .userData(userData) // Add user data to the instance
          .build());

        // Export resource details
        ctx.export("publicIp", instance.publicIp());
    }
}
