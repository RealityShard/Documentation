# Glossary

This page is an ongoing attempt of building a RealityShard glossary.

**Hint:** RealityShard was highly influenced by Java's servlet API. It might help you to read through some of the more helpful servlet books like HeadFirst's Servlets and JSP before reading through the shardlet API. (Thats what i have done: read this book then read through the javax.servlet API to understand whats happening in detail.)

_There should be a dedicated place for the design overview of R:S... or just visit our channel on irc.rizon.net #gwlpr_

---
* **Action**
_Thats a deserialized network packet... well, actually it could be anything recieved from anywhere. Simply put, its something your `Game-App` might want to react to. Actions are given to the `Game-App` by R:S in the form of `ActionEvents`, because everything we want to distribute to any `Shardlets` needs to be put into `Events` (check those for further explanation)._

---
* **ActionEvent**
_`Event` that is able to carry an `Action`._

---
* **ClientVerifier**
_The ClientVerifier is an object that is provided by the `Game-App`. It needs to be passed to the `Context` of that game app. The Context uses that object to determine whether a new client should be accepted or not. This is done by providing the verifier with the first action send by the new client. See the API for details. Usually, a ClientVerifier will be removed from the context after it has successfully accepted a new client, unless you pass the verifier with the 'persistant' flag set._

---
* **Context a.k.a. ShardletContext**
_See `Game-App`_

---
* **Controller**
_See `MVC`_

---
* **Deployment**
_Thats the action of supplying an installed version of R:S with the class files or jars that carry your `Deployment-Descriptors`, `ProtocolFilters`, `Shardlets` or additional libraries. Note that theres a special place where you can put shared libraries that are used by multiple `Game-Apps`/`Shardlets` and/or `ProtocolFilters`. There is a special convention of how the folder-structure of deployed `Game-Apps` and `Protocols` needs to be looking like. See the diagrams of the R:S documentation folder if you want to find out more about that. (But note that they might be out of date and/or confusing :D)._

---
* **Deployment-Descriptor**
_Thats the xml file called either game.xml (for `Game-Apps`) or protocol.xml (for `Protocols`), that describes how R:S should treat your java classes. There's one for each `Game-App` or `Protocol` you have deployed. Later on, R:S will try to load your compiled source files according to these descriptors. They can be difficult to create if it's the first time you create one, so you might want to consult the xml schema files that can be found in the "schema" sub-project of R:S. If you still dont know how to create them, make sure you read a Java tutorial on DD's and understand what they are before asking us to help you on the channel._

---
* **Event**
_An Event is a form of message used for communication between the `Shardlets` within a `Game-App`. Events are also triggered when the server itself needs to communicate with its loaded components, e.g. when it recieves and deserializes incoming network packets (see the `Protocol`-stuff for further info). The `EventAggregator` component acts as a blackboard, where any `Shardlet` or the server itself can post these messages, and any other `Shardlet` (of the same `Game-App`) can read them if they want to (they need to specify `EventHandlers` in that case). Using this event-driven-system makes developing reusable code easier, and reduces the dependencies between `Shardlets`. Low coupling and high cohesion is always a good idea..._

---
* **EventAggregator** (search "EventAggregator-" or "Reactor-Pattern" for additional info)
_The event aggregator is an essential component of every `Game-App`. Its purpose is to distribute `Events` coming from the `Shardlets` or from the Server itself (thats the case when the server recieved a network packet and deserialized it into an `Action`). The `Events` are distributed to all `Shardlets` that implement the right `Eventhandler`._

---
* **EventHandler**
_Thats a method inside a `Shardlet` handling a concrete class implementing the `Event` interface. E.g. an EventHandler that listens for the "GameAppCreatedEvent" could be looking like that: `@EventHandler`/`public void handleStartup(GameAppCreatedEvent event){...}` and might internally trigger other `Events` that tell other `Shardlets` what to do when the `Game-App` was just initialized and started. Note that EventHandlers **must** follow this signature:
a) They must be prefixed by the `@EventHandler` annotation.
b) They must be public and return `void`.
c) Their only parameter has to be of the Event-Type they want to handle.
d) Their name or the parameter-name can be anything (meaningful!) you like._

---
* **Game-App** (a.k.a. Shard)
_A Game-App is a collection of `Shardlets` that can be deployed to the R:S Server. That's what the actual user is going to create. The name is a reference to Java's "Web-Apps". Also note the difference between `Shard` and `Shardlet` (The latter being the actual components of the former). The representation of a Game-App that can be found in the API is called "ShardletContext"._

---
* **Model**
_See `MVC`_

---
* **MVC a.k.a. Model-View-Controller**
_MVC is a concept of sofware design. It's a so called compound-software-design-pattern, and if you are not sure what that means, i recommend reading through some wikipedia articles to get a glimpse of what we are talking about. If we extend the MVC concept onto the design of R:S, it is possible to divide major parts of a `Game-App` according to their responsibilities, and fit them into that concept. It may not follow the original MVC blueprints, but it could be something like that:
A `Game-App` may consist of `Shardlets` that interpret the data of incoming `Actions` (and transform/validate that kind of data). Those would be Controllers or data mediators. Then again there could be other classes (they could also be `Shardlets`) that are instructed by the Controllers that tell them what output data they should transmit to the user. Those would be called Views. The last part is the Model, which represents the so-called businesslogic of an application. The Model could be anything, from one single static class to a whole framework of classes. That last part is also instructed by the Controllers. If you think of the model as a giant state-machine, the Controllers would provide the input to it in the right format, and every state-change of the Model would lead to the Views being updated (either by the state-change itself, using some kind of observer-pattern, or indirectly by being instructed by the Controllers)._

---
* **Protocol**
_This is the name for the user-implemented functionality of how network packets are de/serialized. The Procotol is a set of classes deployed within R:S, that can read and translate and write raw network packets. If you look at the most atomar structure in the protocol-implementation, its a `ProtocolFilter`. Then when you zoom out of that, you will see these filters combined in a static chain that is called `ProtocolFilterChain`. All the chains of all different Protocols combined makes the whole `ProtocolLayer`. There might be default implementations of some major protocols like HTTP be shipped with RealityShard in the future._

---
* **ProtocolFilter**
_Thats a single chain link from the `ProtocolFilterChain`. A single filter is a plain-old-java-class that has a special responsibility within the chain, e.g. packet de/encryption or de/serialization. Filters can either have methods for filtering incoming packets, or outgoing, or both. Actually they need to implement both, as that is specified by the API, but they dont necessarily need to do anything within these methods if that wouldnt make any sense (e.g. a filter that chops and/or assembles incoming packets probably doesnt need to do that with outgoing packets, but a filter that provides de/encryption should be doing that with incoming and outgoing packets).
Note that R:S will create only one instance of each filter of those that have been defined in the `Deployment-Descriptor`. That means a filter must be able to handle multiple packets from different `Sessions` at the same time, due to possible multithreading. As you might have guessed, filters should be implemented as state-less as possbile (meaning they have as few attributes as possible) so that they can be used as parallel as possible. You should not expect a filter to process more than one TCP-packet from the same client-session, but there might be more than one protocol-specific packet within one TCP-packet. Thats the reason why the doIncomingFilter() method may return more than one `Action` in the end._

---
* **ProtocolFilterChain**
_Thats the chain of methods that incoming and/or outgoing packets need to pass to get the actual `Game-Apps` ẁith their `Shardlets`. You can think of that chain as of the `Protocol` itslef or as of all the single `ProtocolFilters` with their specific implementations of the in- and outfilter methods, as specified by the API. You will have to create these Filters on your own depending on the `Protocol` you want to use._

---
* **ProtocolLayer**
_Thats the place where all `ProtocolFilters/Chains` exist. Note that the ProtocolLayer is the highest abstraction of the `Protocol` stuff. There is no such class that represents that layer currently, because all `Protocols` are managed by the "ContextManager". That kind of poor desing might change in the future though._

---
* **Reality-Shard**
_Yes, thats the name of this project. See the project's README for more detailed info._

---
* **Session**
_Thats a single TCP network connection. Sessions should be regarded as persistant, and one user could open multiple Sessions, because using different `Protocols` also means using different Sessions. A session may also contain user-defined attributes (based on string/object-hashmaps) to add additional state to the Session. If you need to handle security stuff for Sessions, it is recommended to use the build in encryption states instead of the attributes. That way you can avoid using the hashmap at all, because using it is always a bad idea in a multithreaded environment. It may be removed in a future version eventually and has only been included to have similar design to Java's servlet.Session._

---
* **Shardlet**
_This is the R:S replacement for Java's Servlets. One Shardlet is a plain-old-java-class that can be loaded by the R:S Server. It's purpose is to handle `Events` coming from other parts of the `Game-App`, or coming from the network as `Actions`. Note that the API is also sometimes called `shardlet`-something, because the actual Java package that holds the API interfaces and classes is com.realityshard.shardlet._

---
* **ShardletContext a.k.a. Context**
_See `Game-App`_

---
* **ShardletEventAction**
_This interface is part of the shardlet API and it's name is actually a really bad choice (and might change in the future). It's purpose is to be an `Action`, that is capable of triggering `Events` when provided with an `EventAggregator`. The reason this strange interface exists is convenience: When we use this instead of regulary `Actions`, we can let them trigger some kind of non-R:S-related `Event` directly within the `Game-App`. There is a story behind that little curiosity: We wanted to hand deserialized packets from the `ProtocolLayer` over to the corresponding `Shardlets`, but because the R:S server cant possibly know what the concrete class of the actions were, it would have to box them within a general-pupose `ActionEvent`, and the `Game-App` would have to figure out what the concrete class of the action was. The trick is, that we can simply let the action trigger the **concrete** ActionEvent on its own, so the right `Shardlet`-specific `EventHandlers` get invoked by the `EventAggregator` automatically._

---
* **View**
_See `MVC`_