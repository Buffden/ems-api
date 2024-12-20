pipeline {
    agent any

    stages {
        stage('Clone Repository') {
            steps {
                echo 'Cloning Repository...'
                git branch: 'main', url: 'https://github.com/Buffden/ems-api'
            }
        }

        stage('Build with Maven') {
            steps {
                echo 'Building the project...'
                sh 'mvn clean package'
            }
        }

        stage('Validate Maven Build') {
            steps {
                echo 'Validating Maven Build'
                sh '''
                    if []; then
                        echo "Maven build successfull, .jar file exists"
                    else
                        echo "Maven build validation failed: .jar file does not exist."
                        exit 1
                    fi
                '''
            }
        }

        stage('Dockerize Application') {
            steps {
                echo 'Building Docker Image...'
                sh '''
                    docker build -t spring-boot-app .
                    docker tag spring-boot-app buffden/spring-boot-app:latest
                '''
            }
        }

        stage('Run Docker Container') {
            steps {
                echo 'Running Docker Container...'
                sh 'docker run -d -p 8081:8080 --name backend-container spring-boot-app'
            }
        }
    }

    post {
        success {
            echo 'Pipeline completed successfully!'
        }
        failure {
            echo 'Pipeline failed.'
        }
    }
}
