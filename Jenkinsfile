def String pomVersion = ''
def String imageName = 'agatalba/tfm-mca-filemanagement-oauth2'
pipeline {
    agent any
    tools {
        maven 'maven-3_8_6' 
    }
    
    stages {
        stage('Checkout') {
            steps {
                checkout([$class: 'GitSCM', branches: [[name: '*/develop']], extensions: [], userRemoteConfigs: [[credentialsId: 'github-user', url: 'https://github.com/agat-prog/mca-filesmanagement-oauth2.git']]])
            }
        }
        stage('Unit Test') {
            steps {
                script {
                    pomVersion = sh script: 'mvn help:evaluate -Dexpression=project.version -q -DforceStdout', returnStdout: true
                }
                
                echo "version -- ${pomVersion}"    
                sh "mvn clean test"                
            }
        }
        stage('Build image') {
            steps {
                sh "mvn compile jib:build -Dimage=${imageName}:${pomVersion} -DskipTests -Djib.to.auth.username=agatalba -Djib.to.auth.password=agat1978#"                
            }
        }        
    }
}