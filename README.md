# wishShare

![GitHub repo size](https://img.shields.io/github/repo-size/MathiasReker/WishShare)
![GitHub Issues](https://img.shields.io/github/issues/MathiasReker/WishShare)
![GitHub contributors](https://img.shields.io/github/contributors/MathiasReker/WishShare)
![GitHub stars](https://img.shields.io/github/stars/MathiasReker/WishShare)
![GitHub forks](https://img.shields.io/github/forks/MathiasReker/WishShare)
![GitHub licence](https://img.shields.io/github/license/MathiasReker/WishShare.svg)

wishShare makes it easy for you to save and share all your wishes with friends and family.

## Using

To use wishShare, follow this link: `<comming soon>`

## Contributing to wishShare

To contribute to wishShare, follow these steps:

1. Fork this repository.
2. Create a branch: `git checkout -b <branch_name>`.
3. Make your changes and commit them: `git commit -m '<commit_message>'`
4. Push to the original branch: `git push origin <project_name>/<location>`
5. Create the pull request.

Alternatively see the GitHub documentation
on [creating a pull request](https://help.github.com/en/github/collaborating-with-issues-and-pull-requests/creating-a-pull-request)
.

## Run on localhost

### Step 1 - download project:

```
git clone git@github.com:MathiasReker/WishShare.git && cd WishShare
```

### Step 2 - install database:

```
mysql -h 127.0.0.1 -P 3306 -u <username> -p < ./src/main/resources/data/install.sql
```

### Step 3 - set properties:

```
cat >> ./src/main/resources/application.properties <<EOL
user=<username>
password=<password>
url=jdbc:mysql://localhost:3306/wishshare
connection=LOCAL
baseUrl=https://localhost:8080/
EOL
```

Connection options:

```
connection=LOCAL
connection=HEROKU
```

### Step 4 - build:

```
mvn install
```

### Step 5 - run:

```
mvn spring-boot:run
```

## Contributors

Thanks to the following people who have contributed to this project:

* [@Andreassim](https://github.com/Andreassim)
* [@Jarkyman](https://github.com/Jarkyman)
* [@MathiasReker](https://github.com/MathiasReker)
* [@Moshizzl3](https://github.com/Moshizzl3)

## License

This project uses the following license: [MIT License](https://github.com/MathiasReker/WishShare/blob/develop/LICENSE).
