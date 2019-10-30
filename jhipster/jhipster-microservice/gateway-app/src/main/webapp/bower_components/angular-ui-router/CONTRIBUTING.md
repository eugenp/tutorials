
# Report an Issue

Help us make UI-Router better! If you think you might have found a bug, or some other weirdness, start by making sure
it hasn't already been reported. You can [search through existing issues](https://github.com/angular-ui/ui-router/search?q=wat%3F&type=Issues)
to see if someone's reported one similar to yours.

If not, then [create a plunkr](http://bit.ly/UIR-Plunk) that demonstrates the problem (try to use as little code
as possible: the more minimalist, the faster we can debug it).

Next, [create a new issue](https://github.com/angular-ui/ui-router/issues/new) that briefly explains the problem,
and provides a bit of background as to the circumstances that triggered it. Don't forget to include the link to
that plunkr you created!

**Note**: If you're unsure how a feature is used, or are encountering some unexpected behavior that you aren't sure
is a bug, it's best to talk it out on
[StackOverflow](http://stackoverflow.com/questions/ask?tags=angularjs,angular-ui-router) before reporting it. This
keeps development streamlined, and helps us focus on building great software.


Issues only! |
-------------|
Please keep in mind that the issue tracker is for *issues*. Please do *not* post an issue if you need help or support. Instead, see one of the above-mentioned forums or [IRC](irc://irc.freenode.net/#angularjs). |

####Purple Labels
A purple label means that **you** need to take some further action.
 - ![Not Actionable - Need Info](ngdoc_assets/incomplete.png): Your issue is not specific enough, or there is no clear action that we can take. Please clarify and refine your issue.
 - ![Plunkr Please](ngdoc_assets/example.png): Please [create a plunkr](http://bit.ly/UIR-Plunk)
 - ![StackOverflow](ngdoc_assets/so.png): We suspect your issue is really a help request, or could be answered by the community.  Please ask your question on [StackOverflow](http://stackoverflow.com/questions/ask?tags=angularjs,angular-ui-router).  If you determine that is an actual issue, please explain why.

If your issue gets labeled with purple label, no further action will be taken until you respond to the label appropriately.

# Contribute

**(1)** See the **[Developing](#developing)** section below, to get the development version of UI-Router up and running on your local machine.

**(2)** Check out the [roadmap](https://github.com/angular-ui/ui-router/milestones) to see where the project is headed, and if your feature idea fits with where we're headed.

**(3)** If you're not sure, [open an RFC](https://github.com/angular-ui/ui-router/issues/new?title=RFC:%20My%20idea) to get some feedback on your idea.

**(4)** Finally, commit some code and open a pull request. Code & commits should abide by the following rules:

- *Always* have test coverage for new features (or regression tests for bug fixes), and *never* break existing tests
- Commits should represent one logical change each; if a feature goes through multiple iterations, squash your commits down to one
- Make sure to follow the [Angular commit message format](https://github.com/angular/angular.js/blob/master/CONTRIBUTING.md#commit-message-format) so your change will appear in the changelog of the next release.
- Changes should always respect the coding style of the project



# Developing

UI-Router uses <code>grunt >= 0.4.x</code>. Make sure to upgrade your environment and read the
[Migration Guide](http://gruntjs.com/upgrading-from-0.3-to-0.4).

Dependencies for building from source and running tests:

* [grunt-cli](https://github.com/gruntjs/grunt-cli) - run: `$ npm install -g grunt-cli`
* Then, install the development dependencies by running `$ npm install` from the project directory

There are a number of targets in the gruntfile that are used to generating different builds:

* `grunt`: Perform a normal build, runs jshint and karma tests
* `grunt build`: Perform a normal build
* `grunt dist`: Perform a clean build and generate documentation
* `grunt dev`: Run dev server (sample app) and watch for changes, builds and runs karma tests on changes.
