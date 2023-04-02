#!/bin/bash

# Installing docker engine if not exists
if ! type docker > /dev/null  # docker를 깔아주는 코드, EC2 인스턴스에는 아무것도 없기때문
then
  echo "docker does not exist"
  echo "Start installing docker"
  sudo apt-get update
  sudo apt install apt-transport-https
  sudo apt install ca-certificates
  sudo apt install curl
  sudo apt install software-properties-common

  curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
  sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu bionic stable"
  sudo apt update

  apt-cache policy docker-ce
  sudo apt install docker-ce
fi

# Installing docker-compose if not exists
if ! type docker-compose > /dev/null
then
  echo "docker-compose does not exist"
  echo "Start installing docker-compose"
  sudo curl -L "https://github.com/docker/compose/releases/download/1.27.3/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
  sudo chmod +x /usr/local/bin/docker-compose
fi

echo "start docker-compose up: aws_username"
sudo docker-compose -f /home/ubuntu/app/docker-compose.yml up --build -d
