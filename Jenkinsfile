node{
    stage('SCM CHECKOUT'){
       
        git 'https://github.com/pieroqb28/Tournaments'
    }
    stage('COMPILE-PACKAGE'){
        
        def mvnHome = tool name: '3.6.0', type: 'maven'

        sh "${mvnHome}/bin/mvn package"
    }
}