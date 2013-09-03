A NetBeans IDE plugin which adds Markdown support. This plugin provides basic syntax highlighting, HTML preview and HTML export when editing a Markdown document.

The plugin is still under development and not considered stable enough to be releases through the NetBeans plugin channel. Unit tests must be added and the lexer implementation has to be revamped.

# What is Markdown?

> Markdown is a text-to-HTML conversion tool for web writers. Markdown allows you to write using an easy-to-read, easy-to-write plain text format, then convert it to structurally valid XHTML (or HTML). Source: <http://daringfireball.net/projects/markdown/>

# Plugin features

- Adds Markdown to your "New File" wizard
- Provides basic syntax highlighting
- Exports your saved file content to an HTML document
- Enables HTML preview of your saved file in your configured web browser
- Lets you customize the HTML output with CSS and alien intelligence (Options->Miscellaneous->Markdown->HTML Export)
- Supports multiple extensions over standard markdown (see https://github.com/sirthias/pegdown, Options->Miscellaneous->Markdown->Extensions)

# Requirements

- NetBeans >= 7.0
- "NetBeans Plugin Development" plugin must be installed if you want to compile your own binary package.

# Installation

For the time beeing there is a nbm binary in the "nbm" directory directly in the Git repository.

[Download](https://github.com/madflow/flow-netbeans-markdown/raw/master/nbm/flow-netbeans-markdown.nbm)

You may also compile a binary yourself.

 1. git clone git://github.com/madflow/flow-netbeans-markdown.git
 2. Open the folder with NetBeans (Open Project)
 3. (Configure Target Platform if needed)
 4. Choose "Create NBM" from the project menu
 5. Install the plugin with: Tools -> Plugins -> Downloaded

# Authors

- Florian Reiss <https://github.com/madflow/>

# Contributions

- junichi11 <https://github.com/junichi11>

Contributions are always welcome and greatly appreciated!

# Resources

- https://github.com/sirthias/pegdown/ : A pure-Java Markdown processor based on a parboiled PEG parser supporting a number of extensions.
- https://bitbucket.org/tcolar/fantomide : Fantom plugin/IDE using Netbeans. Example for implementing a Lexer with a parboiled parser.
- http://daringfireball.net/projects/markdown/ : Home of the Markdown (Basics, Syntax)
- http://openiconlibrary.sourceforge.net/ : Icons
