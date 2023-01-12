def String pomVersion = ''
pipeline {
    environment {
        NAMESPACE = "${env.BRANCH_NAME == "main" ? "tfm-prod-agat-prog" : "tfm-pre-agat-prog"}"
        MYSQL_HOST = "${env.BRANCH_NAME == "main" ? "mysql-service.tfm-prod-svc-agat-prog.svc.cluster.local" : "mysql-service.tfm-pre-svc-agat-prog.svc.cluster.local"}"
        DEPLOY = "${env.BRANCH_NAME == "main" || env.BRANCH_NAME == "develop" ? "true" : "false"}"
        BUILD = "${env.BRANCH_NAME == "develop" || env.BRANCH_NAME.startsWith("release") || env.BRANCH_NAME == "main" ? "true" : "false"}"
        REGISTRY = 'agatalba/tfm-mca-filemanagement-oauth2'
    }
	options {
	    buildDiscarder(logRotator(numToKeepStr: "2"))
		disableConcurrentBuilds()  
	}    
    agent any
    tools {
        maven 'maven-3_8_6' 
    }
    
    stages {
        stage('Print environment') {
            steps {
                echo "env.BRANCH_NAME -- ${env.BRANCH_NAME}"
                echo "NAMESPACE -- ${NAMESPACE}"
                echo "REGISTRY -- ${REGISTRY}"
                echo "BUILD -- ${BUILD}"
                echo "DEPLOY -- ${DEPLOY}"
            }
        }    
        stage('Unit Test') {
            steps {
                script {
                    pomVersion = sh script: 'mvn help:evaluate -Dexpression=project.version -q -DforceStdout', returnStdout: true
                }
                echo "env.BRANCH_NAME -- ${env.BRANCH_NAME}"
                echo "BUILD -- ${BUILD}"
                echo "DEPLOY -- ${DEPLOY}" 
                echo "version -- ${pomVersion}"    
                sh "mvn clean test"                
            }
        }
        stage('Checkstyle') {
            steps {
                sh "mvn checkstyle:check"                
            }
        }   
        stage('Coverage check') {
            steps {
                sh "mvn verify"                
            }
        }              
        stage('Build image') {
            when {
                environment name: 'BUILD', value: 'true'
            }
            steps {
            	echo "version -- ${REGISTRY}" 
                sh "mvn compile com.google.cloud.tools:jib-maven-plugin:3.2.0:build -Dimage=${REGISTRY}:${pomVersion} -DskipTests -Djib.to.auth.username=agatalba -Djib.to.auth.password=agat1978#"                
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
                sh "helm upgrade -n ${NAMESPACE} -f helm/values.yaml --set namespace=${NAMESPACE} --set image.tag='${pomVersion}' --set mysql.host=${MYSQL_HOST}  oauth2-release helm/"
            }
        }              
    }
}
