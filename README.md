**Running SMTP4Dev with Docker**

SMTP4Dev is a fake SMTP server designed for developers. It traps and displays emails sent by your development and testing efforts, making it easier to inspect the content and debug email functionalities without sending real emails.

To quickly set up SMTP4Dev using Docker, you can use the following command:


`docker run --rm -it -p 3000:80 -p 2525:25 rnwood/smtp4dev:v3
`

What This Command Does:
docker run tells Docker to run a container.

* **--rm** automatically removes the container file system when the container exits. This is useful for not leaving behind any temporary containers.

* **-it** allows you to interact with the container via the terminal.
* **-p 3000:80** maps port 80 inside the container (the web UI port of smtp4dev) to port 3000 on your host, allowing you to access the web interface at http://localhost:3000.
* **-p 2525:25** maps port 25 inside the container (the SMTP server port) to port 2525 on your host, enabling your applications to send email to smtp4dev by connecting to localhost on port 2525.
* **rnwood/smtp4dev:v3** specifies the Docker image and tag to use. In this case, version 3 of rnwood/smtp4dev.


After running this command, you can view emails sent by your applications by accessing the SMTP4Dev web interface at http://localhost:3000