#
# Resource definitions
#

data "aws_ami" "apache" {
  filter {
    name   = "name"
    values = [var.ami_name]
  }

  filter {
    name   = "virtualization-type"
    values = ["hvm"]
  }

  owners = [var.ami_owner]

  most_recent = true
}

resource "aws_instance" "web" {
  ami = data.aws_ami.apache.id
  instance_type = "t2.micro"
  subnet_id = aws_subnet.frontend.id
}
resource "aws_subnet" "frontend" {
  vpc_id = aws_vpc.apps.id
  cidr_block = "10.0.1.0/24"
}

resource "aws_vpc" "apps" {
  cidr_block = "10.0.0.0/16"
}
