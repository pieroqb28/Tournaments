node{
    stage('SCM CHECKOUT'){
       
        git 'https://github.com/pieroqb28/Tournaments'
    }
    stage('COMPILE-PACKAGE'){
        
        def mvnHome = tool name: '3.6.0', type: 'maven'

        bat "C:\"Program Files (x86)"\Jenkins\workspace\Piepeline2>C:\"Program Files (x86)"\Jenkins\tools\hudson.tasks.Maven_MavenInstallation\3.6.0/bin/mvn package"
    }
}