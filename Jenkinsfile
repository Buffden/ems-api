pipeline {
    agent {
        docker {
            image 'maven:3.8.5-openjdk-17' // Use a Docker image with Maven and OpenJDK
        }
    }
    environment {
        DB_CREDENTIALS = credentials('db-credentials') // Jenkins credentials ID
    }

    stages {
        stage('Clone Repository') {
            steps {
                echo 'Cloning Repository...'
                git branch: 'main', url: 'https://github.com/Buffden/ems-api'
            }
        }

        stage('Build Application') {
            steps {
                echo 'Building the application...'
                sh 'mvn clean package'
            }
        }

        stage('Validate Maven Build') { // Validation stage added here
            steps {
                echo 'Validating Maven Build...'
                sh '''
                    if [ -f target/employee-management-system.jar ]; then
                        echo "Build validation successful: .jar file exists."
                    else
                        echo "Build validation failed: .jar file does not exist."
                        exit 1
                    fi
                '''
            }
        }

        stage('Run Application with Docker') {
            steps {
                echo 'Starting PostgreSQL and Spring Boot...'
                sh '''
                    # Export environment variables
                    export DB_CREDENTIALS_USR=${DB_CREDENTIALS_USR}
                    export DB_CREDENTIALS_PSW=${DB_CREDENTIALS_PSW}

                    # Start services
                    docker-compose up -d --build
                '''
            }
        }

        stage('Run Tests') {
            steps {
                echo 'Running tests...'
                sh 'mvn test'
            }
        }

        stage('Stop Services') {
            steps {
                echo 'Stopping services...'
                sh 'docker-compose down'
            }
        }
    }

    post {
        success {
            echo 'Pipeline completed successfully!'
        }
        failure {
            echo 'Pipeline failed. Please check the logs.'
        }
    }
}
