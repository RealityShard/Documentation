_The developers guide: How to setup your dev-environment_


# The Linux guide:

## What do you need?
* **Linux**, any distro will probably do
* **Git**
* **Java-Development-Kit 7** (aka JDK 7 or JDK 1.7) (Consider using another guide from the inter-webs here)
* **Java IDE** (recommended: NetBeans)
* **Maven 3.X** (Check the maven sites out there on how to install maven, your linux packages might be out-of-date)
* Optional: UMLet, to view and edit the diagrams

_... and as usual: patience_

## Steps

1. Get to know the tools above and how they work, at least basically

2. Our project does employ the git-flow workflow - Read this: https://github.com/diaspora/diaspora/wiki/Git-Workflow (this is diaspora specific stuff, but it does cover the basics quite well)

3. Maven may be new to you and imo you don't need to know it that well, because it's just a tool useful for managing the project. Yet, if you want to use its features, assure the following: http://stackoverflow.com/questions/5755137/maven-3-installation

4. Set your IDE to work with your previously downloaded (and installed :D) JDK 1.7

5. Clone this repo with Git: https://github.com/RealityShard/RealityShard

6. Install it using the install.sh file within the repo

7. (Optional) If you are using NetBeans you can simply open the project folder you just cloned and it will load the maven project automatically. Also, you don't need to clean up the repository before adding things, because NetBeans doesn't create any files there.

8. (Optional) Now, when it comes to Eclipse:

   8.1. Integrate the downloaded files into your Eclipse workspace, you might need to _import_ them somehow. Note that Eclipse has an extra option for maven projects.

   8.2. Make sure that you don't add your IDE-generated files within the repository. If that happens they will be deleted from the repository by one of the other team members.

   8.3. DO NOT setup Eclipse to manage the repository and Git stuff for you, idk if it works with Github and you can't really control what it's doing with your files. In general, after messing around with Eclipse a couple of hours i figured it's better to install AS FEW plugins as possible because you might end up with totally error-prone and confusing IDE. (I used to have UMLet and FindBugs installed within the Eclipse-for-Java-devs package)

9. (Optional) Last but not least: If your using IntelliJ IDEA, same as with Eclipse, make sure that you don't upload any IDE generated files. Especially when they are some large database files!

10. Configure your IDE. That's no joke... you've got a lot of coding-convention/style options there and it's pretty useful if they follow our [style-guide](https://github.com/GameRevision/GWLP-R/wiki/StyleGuide)
