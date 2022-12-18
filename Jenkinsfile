def String pomVersion = ''
pipeline {
    environment {
        NAMESPACE = "${env.BRANCH_NAME == "main" ? "tfm-prod-agat-prog" : "tfm-pre-agat-prog"}"
        DEPLOY = "${env.BRANCH_NAME == "main" || env.BRANCH_NAME == "develop" ? "true" : "false"}"
        BUILD = "${env.BRANCH_NAME == "develop" || env.BRANCH_NAME.startsWith("release") ? "true" : "false"}"
        SUFIJO = "${env.BRANCH_NAME.startsWith("release") ? "-rc" : "__"}"
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
                echo "SUFIJO -- ${SUFIJO}"                            
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
        stage('RC version') {
            when {
                expression {
                    return env.BRANCH_NAME.startsWith("release")
                }
            }
            steps {
                pomVersion = pomVersion + "-rc"
                echo "pomVersion"                
            }
        }        
        stage('Build image') {
            when {
                environment name: 'BUILD', value: 'true'
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
                sh "helm upgrade -n ${NAMESPACE} -f helm/values.yaml --set image.tag='${pomVersion}${SUFIJO}'  oauth2-release helm/"
            }
        }              
    }
}