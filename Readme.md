A Netbeans IDE plugin which adds Markdown support. This plugin provides basic syntax highlighting, HTML preview and HTML export when editing a Markdown document.

The plugin is still under development and not considered stable enough to be releases through the Netbeans plugin channel. Unit tests must be added and the lexer implementation has to be revamped.

# What is Markdown?

> Markdown is a text-to-HTML conversion tool for web writers. Markdown allows you to write using an easy-to-read, easy-to-write plain text format, then convert it to structurally valid XHTML (or HTML). Source: <http://daringfireball.net/projects/markdown/>

# Requirements

- Netbeans >= 7.0

# Installation

For the time beeing there is a nbm binary in the "nbm" directory directly in the Git repository.

You may also compile a binary yourself.

 1. git clone git://github.com/madflow/flow-netbeans-markdown.git
 2. Open the folder with NetBeans (Open Project)
 3. Choose "Create NBM" from the project menu
 4. Install the plugin with: Tools -> Plugins -> Downloaded

# Authors

- Florian Reiss <http://github/madflow/>

# Credits

- sirthias (Mathias) https://github.com/sirthias/pegdown/
 - A pure-Java Markdown processor based on a parboiled PEG parser supporting a number of extensions