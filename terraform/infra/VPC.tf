data "aws_vpc" "mslanchonetecliente_vpc" {
  filter {
    name   = "tag:Name"
    values = ["${var.project_name}-vpc"]
  }
}

data "aws_subnets" "mslanchonetecliente_public_subnets" {
  filter {
    name   = "vpc-id"
    values = [data.aws_vpc.mslanchonetecliente_vpc.id]
  }

  filter {
    name   = "tag:type"
    values = ["public"]
  }
}