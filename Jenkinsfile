node {
    def WORKSPACE = "var/lib/jenkins/workspace/springboot-deploy"
    def dockerImageTag = "springboot-deploy${env.BUILD_NUMBER}"

    try{
        stage('Clone Repo'){
            git url: 'https://github.com/FathiahHusna/cook-with-love.git',
            credentialsId:  'springdeploy-user',
            branch: 'main'
        }
        stage('Build Docker'){
//             dockerImage = docker.build("springboot-deploy${env.BUILD_NUMBER}", ".")
                sh "docker build -t ${dockerImageTag} ."
                sh "docker images | grep springboot-deploy || echo 'Image build failed!'"
        }
        stage('Deploy Docker'){
            echo "Docker Image Tag Name: ${dockerImageTag}"
            sh "docker stop springboot-deploy || true && docker rm springboot-deploy || true"
//             sh "docker run --name springboot-deploy -d -p 8081:8080 springboot-deploy:${env.BUILD_NUMBER}"
            sh "docker run --name springboot-deploy -d -p 8081:8080 ${dockerImageTag}"
        }
    }catch(e){
        throw e
    }
}