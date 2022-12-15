def String pomVersion = ''
pipeline {
    environment {
        NAMESPACE = "${env.BRANCH_NAME == "main" ? "tfm-prod-agat-prog" : "tfm-pre-agat-prog"}"
        DEPLOY = "${env.BRANCH_NAME == "main" || env.BRANCH_NAME == "develop" ? "true" : "false"}"
        NAME = "${env.BRANCH_NAME == "main" ? "example" : "example-staging"}"
        REGISTRY = 'agatalba/tfm-mca-filemanagement-oauth2'
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
            	echo "version -- ${REGISTRY}" 
                sh "mvn compile jib:build -Dimage=${REGISTRY}:${pomVersion} -DskipTests -Djib.to.auth.username=agatalba -Djib.to.auth.password=agat1978#"                
            }
        }  
        stage('Deploy into Kubernetes') {
            when {
                environment name: 'DEPLOY', value: 'true'
            }          
            agent {
                docker {
                    image 'dtzar/helm-kubectl'
                    args  '-u root -v /home/agat/.kube:/root/.kube'
                }
            }  
            steps {
                checkout([$class: 'GitSCM', branches: [[name: '*/develop']], extensions: [], userRemoteConfigs: [[credentialsId: 'github-user', url: 'https://github.com/agat-prog/mca-filesmanagement-oauth2.git']]])

                sh "helm upgrade -n ${NAMESPACE} -f helm/values.yaml --set image.tag='${pomVersion}'  oauth2-release helm/"
            }
        }              
    }
}

