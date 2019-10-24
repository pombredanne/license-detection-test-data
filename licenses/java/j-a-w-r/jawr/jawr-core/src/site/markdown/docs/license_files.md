Introduction
------------

Most often you will use Jawr to minify the resources that get bundled
onto a file. This means that all comments will be removed from the
source and served to clients in that form. However, if you use open
source libraries you are normally required to include some sort of
license with them. Also, you may want to include your own copyright and
license terms with your resources. This kind of licensing data always
comes in the form of source comments, but if they are written in your
resources, minification will delete them. To solve this problem, you can
use the license files feature in Jawr.  

### License files

A license file is simply a file named **.license** that contains
comments with licensing info, either yours or from of an open source
library. Its content is copied verbatim to the top of the generated
bundle file. Therefore you must be careful to have only well formed
comments in this file or errors may arise in your bundles. Also, the
license file must use the same encoding as your resources to prevent
problems when creating the bundle. As an example, this would be the
content of the license file we should add to any bundle containing the
Prototype.js library:


    /*  Prototype JavaScript framework, version 1.6.0
     *  (c) 2005-2007 Sam Stephenson
     *
     *  Prototype is freely distributable under the terms of an MIT-style license.
     *  For details, see the Prototype web site: http://www.prototypejs.org/
     *
     *--------------------------------------------------------------------------*/
            

In order to be able to use the license files, you must have the licenses
postprocessor active. Normally you don't have to do anything for this to
happen since it is on by default, but keep it in mind if you customize
the postprocessors.  

A license file can be included explicitly as part of a mapping, or
implicitly by placing it in a mapped directory. For example, if you had
this directory structure:

![A sample directory structure](../images/licenses/dir_sample.png)
you could map as follows:

            ...     
            jawr.js.bundle.fooBundle.id=/bundles/fooBundle.js
            jawr.js.bundle.fooBundle.mappings=/someDir/**
            
            jawr.js.bundle.barBundle.id=/bundles/barBundle.js
            jawr.js.bundle.barBundle.mappings=/bar.js, /.license
            

In the case of fooBundle, the license is included implicitly because of
the fact that it is within a mapped directory. Such mapping
(/someDir/\*\*) would include any license file in any subdirectory of
someDir as well.  

If more than one file in the same directory requires a license, you
simply add both of them to the same license file.  

For barBundle, the license is explicitly added to the mapping. Note that
even though normally the order of files added to a bundle corresponds to
the order in which you map them, licenses will be at the top of the file
regardless of its position in the mapping.
