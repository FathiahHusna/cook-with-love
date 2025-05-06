pipeline {
    agent {
        docker {
            image 'docker:20.10.16-dind'
            args '--privileged' // Might be needed for Docker-in-Docker
        }
    }

    environment {
        dockerImageTag = "springboot-deploy${env.BUILD_NUMBER}"
    }

    stages {
        stage('Clone Repo') {
            steps {
                git url: 'https://github.com/FathiahHusna/cook-with-love.git',
                    credentialsId: 'springdeploy-user',
                    branch: 'main'
            }
        }

        stage('Build Docker') {
            steps {
                sh "docker build -t ${dockerImageTag} ."
            }
        }

        stage('Deploy Docker') {
            steps {
                echo "Docker Image Tag Name: ${dockerImageTag}"
                sh "docker stop springboot-deploy || true && docker rm springboot-deploy || true"
                sh "docker run --name springboot-deploy -d -p 8081:8080 ${dockerImageTag}"
            }
        }
    }
}
