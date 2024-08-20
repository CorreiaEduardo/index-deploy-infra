pipeline {
    agent any

    parameters {
        choice(name: 'SERVICE_NAME', 
               choices: ['index-app', 'index-api', 'all'], 
               description: 'Select the service(s) to redeploy')
    }

    stages {
        stage('Pull latest image(s) from dockerhub') {
            steps {
                script {
                    if (params.SERVICE_NAME == 'all') {
                        sh "docker-compose -f ./docker-compose.yml pull"
                    } else {
                      sh "docker-compose -f ./docker-compose.yml pull ${params.SERVICE_NAME}"
                    }
                }
            }
        }

        stage('Stop and remove service(s)') {
            steps {
                script {
                    if (params.SERVICE_NAME == 'all') {
                        sh "docker-compose -f ./docker-compose.yml down"
                    } else {
                      sh "docker-compose -f ./docker-compose.yml rm -sf ${params.SERVICE_NAME}"
                    }
                }
            }
        }

        stage('Start service(s)') {
            steps {
                script {
                    if (params.SERVICE_NAME == 'all') {
                        sh "docker-compose -f ./docker-compose.yml up -d --force-recreate"
                    } else {
                      sh "docker-compose -f ./docker-compose.yml up -d ${params.SERVICE_NAME}"
                    }
                }
            }
        }
    }

    post {
        always {
            script {
                // Clean up old Docker images that are not in use
                sh 'docker image prune -f'
            }
        }
    }
}
