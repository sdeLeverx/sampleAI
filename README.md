# Spring AI

## Building and running

Run the project from your IDE or use the Maven command line
```
./mvnw spring-boot:run
```

## Access the endpoint for Prompt

To get a response to the default request of "Tell me the best football club in the world" using the `http` command line utility

```shell
http http://localhost:8080/ai/prompt
```
or using `curl`
```shell
curl http://localhost:8080/ai/prompt
```
A sample response is

```text
As an AI, I do not have personal opinions. However, some of the football clubs that are often considered the best in the world include FC Barcelona, Real Madrid, Bayern Munich, and Liverpool FC. Ultimately, the best football club in the world is subjective and can vary depending on individual preferences and criteria.
```

Now using the `text` request parameter:

```shell
http GET localhost:8080/ai/simple message=='Tell me a joke about software engineer' 
```
or using `curl`
```shell
curl --get  --data-urlencode 'message=Tell me a joke about software engineer' http://localhost:8080/ai/prompt 
```
# Spring AI - Prompt Templating 

There is a Spring REST Controller named `PromptTemplateController`.

The `PromptTemplateController` shows how to use the StringTemplate Engine and the Spring AI `PromptTemplate` class.
In the `resources\prompts` directory is the file `joke-prompt`.  
That file is loaded using the Spring `Resource` abstraction in the controller as shown below


```java
    @Value("classpath:/prompts/joke-prompt.st")
    private Resource jokeResource;
```

The files contents are

```text
Tell me a {adjective} joke about {topic}
```

The `PromptTemplateController` accepts HTTP GET requests at `http://localhost:8080/ai/promptTemplate` with two optional parameters

* `adjective`, whose default value is `funny`
* `topic`, whose default value is `cows`

# Spring AI - Prompt Roles

There is a Spring REST Controller named `RoleController`.

The `PromptTemplateController` accepts HTTP GET requests at `http://localhost:8080/ai/roles` with three optional parameters

* `message` The user request message. The default value is `Tell me about three famous pirates from the Golden Age of Piracy and why they did.  Write at least a sentence for each pirate.`
* `name`, The name of the AI assistant.  The default value is `Bob`
* `voice`, The style of voice that the AI assistant will use to reply.  The default value is `pirate`

## Roles

For each role, a message is created that will be sent as part of the Prompt to the AI model.

The User message is the content of the 'message'. 

The System message is what sets the context for the AI Model to respond.

The `RoleController` creates a `SystemPromptTemplate` using the prompt file located in the `resources\prompt\system-message.st`

The file's contents are

```text
You are a helpful AI assistant.
You are an AI assistant that helps people find information.
Your name is {name}
You should reply to the user's request with your name and also in the style of a {voice}.
```

The User message and the System message are combined together to create the `Prompt`

```java
Prompt prompt = new Prompt(List.of(userMessage, systemMessage));
```

# Spring AI - Output Parsers

There is a Spring REST Controller named `OutputParserController`.

The `OutputParserController` accepts HTTP GET requests at `http://localhost:8080/ai/output` with one optional parameter

* `actor` The actor's name.  The default value is `Jeff Bridges`

The actors name is used in the hardcoded text for the prompt

```text
String userMessage = """
        Generate the filmography for the actor {actor}.
        {format}
        """;
```

The `format` variable is obtained from the `OutputParser`

## BeanOutputParser

The `BeanOutputParser` generates an OpenAI JSON compliant schema for a JavaBean and provides instructions to use that schema when replying to a request.

```java
var outputParser = new BeanOutputParser<>(ActorsFilms.class);
String format = outputParser.getFormat();
```

The response from the OpenAI is then parsed into the class `ActorsFilms`

```java
ActorsFilms actorsFilms = outputParser.parse(generation.getOutput().getContent());
```
