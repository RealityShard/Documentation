## Overview
    Project:       Reality:Shard 
    Info:          It's a generic game-server based on Java's servlets.
    Languages:     Java
    People:        _rusty (author)

**So what is that whole thing now?**

Reality:Shard is a generic online server that is based on the design of the `javax.servlet` framework, but is not compatible with it. It was created to be a basis for GWLP:R and other mmo game-servers, yet at the same time providing the possibility to interface with other network clients, such as HTTP based ones. With this new project, i'm spending a lot more time on the software design of this server, which directly affects the projects build upon it. Reality:Shard is an independent application that does nothing else than loading the game-specific code like a plugin. This principle is called inversion of control and will have a lot of benefits, like taking responsibility from the actual GWLP:R code, making it more lightweight and easy to change. It will also produce a highly reusable code base, reducing the time spent on designing other projects. Additionally, everything is written in Java, which might affect performance, but has a huge effect on code simplicity, productivity and portability. My goal for this project is to provide a very _simple_, yet powerful framework that reduces the code complexity on the plugin side, by handling most of the confusing stuff within the server. Developers using the API do not need to know how the R:S server works to be able to build game servers, which is comparable to how servlet web-servers/containers work, or do you know what tomcat or glassfish is doing with your servlet classes to make communication with a common browser possible?


### Install notes for Developers:
 - _Have a look at the existing guide here:_  [The Developer's Guide](https://github.com/rusty-gr/Reality-Shard/wiki/Dev-HowTo)
 - _Check out our glossary, if you'r lost in translation:_ [The Glossary](https://github.com/rusty-gr/Reality-Shard/wiki/Glossary)