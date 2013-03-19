The developers guide: How to setup your dev-environment

_This will soon be updated._


# The Linux guide:

## What do you need?
* **Linux**, e.g. Ubuntu or any other major distro
* **Git**
* **Java-Development-Kit 7** (aka JDK 7 or JDK 1.7) (Consider using another guide from the inter-webs here)
* **Java IDE** (recommended: NetBeans, Eclipse or IntelliJ IDEA)
* **Maven 3.X** (Check the maven sites out there on how to install maven)
* Optional: UMLet plugin for Eclipse (or standalone, to view and edit the diagrams)

_... and as usual: patience_

## Steps

1. Get to know the tools above and how they work, at least basically

2. Set your IDE to work with your previously downloaded (and installed :D) JDK 1.7

3. Maven may be new to you and imo you don't need to know it that well, because it's just a tool useful for managing the project. Yet, if you want to use its features, assure the following:  

   3.1. Java needs to be on your "PATH" for Maven to find it. Follow http://www.cyberciti.biz/faq/linux-unix-set-java_home-path-variable/ to do so  

   3.2. Do the same for "M2_HOME=/usr/maven/apache-maven-2.2.1" where M2_HOME points to the location of your extracted Maven download. Also add M2_HOME to your PATH variable.

4. Clone the git repository (Check out how to do that if you haven't used Git yet)

5. (Optional) If you are using NetBeans you can simply open the project folder and it will do anything else for you. 
Additionally you don't need to clean up the repository, because NetBeans doesn't create any files there.

6. (Optional) Now, when it comes to Eclipse:

   6.1. Integrate the downloaded files into your Eclipse workspace, you might need to _import_ them somehow. Note that Eclipse has an extra option for maven projects.

   6.2. Make sure that you don't integrate your IDE-generated files within the repository. If that happens they will be deleted from the repository.

   6.3. DO NOT setup Eclipse to manage the repository and Git stuff for you, idk if it works with Github and you can't really control what it's doing with your files. In general, after messing around with Eclipse a couple of hours i figured it's better to install AS FEW plugins as possible because you might end up with totally error-prone and confusing IDE. (I used to have UMLet and FindBugs installed within the Eclipse-for-Java-devs package)

7. (Optional) Last but not least: If your using IntelliJ IDEA, like with Eclipse, make sure that you don't upload any IDE generated files. Especially when they are some large database files!

8. Configure your IDE. That's no joke... you've got a lot of coding-convention/style options there and it's pretty useful if they follow our [style-guide](https://github.com/GameRevision/GWLP-R/wiki/StyleGuide)
