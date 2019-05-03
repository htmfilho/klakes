# Change Log
All notable changes to this project will be documented in this file. This change log follows the conventions of [keepachangelog.com](http://keepachangelog.com/).

## [0.2.0] - 2019-05-02

### Added

- The concept's page shows other concepts in which the current concept is _influenced by_ and _influences_ other concepts.
- The concept's page shows the label associated with the concept.
- The list of content in the concept's page shows the date the content was last modified.
- A page /tags shows a cloud of tags where the font size of each tag is propotional to the volume of associated content.
- Using a logging library to investigate issues.
- Wiki authentication parameters are stored in memory and discarded after logout.

## 0.1.0 - 2019-04-16

### Added
- Imports a knowledge model from an external json file. The file can be imported multiple times to update the knowledge model.
- Shows the knowledge model as a dynamic graph organized in multiple levels.
- Navigates from a level to another by clicking on a concept of the current level.
- Search the wiki for content according to their labels/tags which are associated with concepts.
- Shows the list of content from the wiki related to the concept.
- Navigates to the page on the wiki when clicking on a content listed in the concept's page.
- Reads wiki connectivity parameters from a config file.
- [Blog Post](http://www.hildeberto.com/klakes/jekyll/update/2019/04/16/minimal-viable-product.html)

[Unreleased]: https://github.com/htmfilho/klakes/compare/0.2.0...HEAD
[0.2.0]: https://github.com/htmfilho/klakes/compare/0.1.0...0.2.0