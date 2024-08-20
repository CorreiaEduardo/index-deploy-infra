https://yetkintimocin.medium.com/creating-a-local-jenkins-server-using-docker-2e4dfe7b5880

docker exec <container> cat /var/jenkins_home/secrets/initialAdminPassword

docker cp ./index.yml f3babe1f6547a895cefc729dbd24d106c2abab4d2514a7bae768d147ae835236:/var/jenkins_home/workspace/index/docker-compose.yml
