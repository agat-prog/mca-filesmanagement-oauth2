def String pomVersion = ''
def String imageName = 'agatalba/tfm-mca-filemanagement-oauth2'
pipeline {
    environment {
        DEPLOY = "${env.BRANCH_NAME == "main" || env.BRANCH_NAME == "develop" ? "true" : "false"}"
        NAME = "${env.BRANCH_NAME == "main" ? "example" : "example-staging"}"
        REGISTRY = 'davidcampos/k8s-jenkins-example'
    }
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
            when {
                environment name: 'DEPLOY', value: 'true'
            }        
            steps {
                sh "mvn compile jib:build -Dimage=${imageName}:${pomVersion} -DskipTests -Djib.to.auth.username=agatalba -Djib.to.auth.password=agat1978#"                
            }
        }        
    }
}

