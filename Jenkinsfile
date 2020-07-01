node{
    stage('SCM CHECKOUT'){
       
        git 'https://github.com/pieroqb28/Tournaments'
    }
    stage('COMPILE-PACKAGE'){
        
        def mvnHome = tool name: '3.6.0', type: 'maven'

        sh "${mvn package}/bin/mvn package"
    }
}