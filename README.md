# wp-to-jekyll

A simple commandline tool to convert a WordPress Blog to [Jekyll](https://jekyllrb.com/).

## Why?

When transitioning servers I was unable to get WordPress to re-install. I decided to take this opportunity to transition to Jekyll. But Jekyll's tools for converting a WordPress Blog were expecting the WordPress Blog to be running. I spent probably 12 hours before I gave up trying to get WordPress up and then started writing this utility. (This took less than half the time I took trying to get WP running to build.)

## Limitations

This utility was written with my needs in mind. I tried to write it in such a way that it could be reused if anyone else was in need, but you may need to make some tweaks if your use case differs from mine. You will certainly need to update any links you have made to your posts. Though the images should work.

Because I wanted to redo my post organization anyway I didn't research blog categories so that's not done at all.

## Implementation

This utility expects a SQL server to be running and that you have a WordPress includes directory that you want copied. It does not transform HTML to markdown nor does it validate the HTML of the posts. I found when using this that I had to manually tweak a few of my posts to make them display correctly (especially those that contained html code examples) you will likely need to do the same.

Comments will also be copied into the body of your post (though you can override this with an option) under the div with class "archived comments" that way you can style it however you choose. Again, if you want to fork this and change the way that works you may.

## Usage

* Make backups and backups of your backups.
* Make sure you have MySQL running
* Know where the assets folder is for WordPress
* on your computer's file system
  * create or edit a file /etc/hosts.allow
  * add the line `mysqld : ALL : ALLOW`
* run jar located in the target directory of this repository
  * Run `java -jar wherever/you/have/it/wp-to-jekyll-0.1.0-SNAPSHOT-standalone.jar` with required and optional options
    * See option list below.
  * Example:

    `java -jar ~/wp-to-jekyll-0.1.0-SNAPSHOT-standalone.jar --db-name "name" --db-user "name" --db-password "password" --wp-uploads "/Applications/MAMP/htdocs/wp/wp-content/uploads"`
* cleanup
  * remove `mysqld : ALL : ALLOW` if desired

If you make changes to the code you'll need to have [Leiningen](http://leiningen.org/) installed. Run `lein uberjar` to get a new jar file, run that jar rile with the options you want.

## Options

| Option | Description | Required | Default |  Notes |
|---|---|---|---|---|
| --db-name | name of database | yes | none | |
| --db-user | user with access to database | yes | none | |
| --db-password | password of database | yes | none | |
| --wp-uploads | location of WordPress Uploads | yes | none | Copies the directory you provide to the --output directory |
| --output | directory location where files should be stored | no | current directory | |
| --overwrite-files | whether to overwrite any existing files with clashing names | no | false | |
| --db-subprotocol | usually mysql | no | "mysql" | |
| --db-url | host of database | no | "127.0.0.1" | Not tested with anything other than localhost |
| --db-port | port of database | no | 3306 | |


## One Last Note

It probably obvious, but I only needed this once so I don't intend to maintain this app long term. With that in mind I tried to make the code organized if other needed this utility but needed to alter it. It uses Clojure and the main function is located in `wp-to-jekyll.console` namespace. I hope you find this utility useful.

## License

Copyright © 2016 Mahon Baldwin

Distributed under the Eclipse Public License either version 1.0.
