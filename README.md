# Magnolia CMS Jade Renderer

[Jade](http://jade-lang.com) renderer for [Magnolia CMS](http://www.magnolia-cms.com).

The implementation is based on [Jade4j](https://github.com/neuland/jade4j).

## Contents

- [Install](#install)
- [Usage](#usage)
    - [Mixins](#mixins)
    - [Configuration](#configuration)
    - [Model](#model)
    - [Blossom](#blossom)
    - [Model Defaults](#api-model-defaults)
    - [Template Loader](#api-template-loader)
- [Example](#example)

## Install

### Maven

If your project is managed by Maven, just add the following dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>com.openmindlab</groupId>
    <artifactId>magnolia-rendering-jade</artifactId>
    <version>1.0</version>
</dependency>
```

### Manually

Download latest release here:


## Usage

With magnolia-rendering-jade module you can code your templates, areas or components using jade lang.

### Mixins

You need to add:
```
include /magnolia
```
in every jade template in order to use the built-in mixins to emulate magnolia jsp tags:

| Jade mixin name  | Magnolia Jsp tag |
|------------------|------------------|
| `+cms_page`      | `cms:page`       |
| `+cms_area`      | `cms:area`       |
| `+cms_component` | `cms:component`  |

Jsp tag attributes can be passed to mixins as jade attributes, i.e.
```jade-lang
+cms_area(name="a-area")
```

### Configuration

You can tune the configuration of the jade renderer by the `/server/rendering/jade` node in the config repository.
Refers to Jade4j configuration for details.


### Model

The following variables are available in jade templates:

| Variable | Description |
| -------- | ----------- |
| content  | content being rendered |
| ctx      | MgnlContext instance |
| contextPath | current context path |
| aggregationState | aggregation state |
| i18n | wrapper for i18n messages |

### Blossom

TBD


## Example

YAML template definition:
```yaml
title: Homepage
templateScript: /templates/t-homepage.jade
renderType: jade
visible: true
dialog: jade:pages/t-homepage
areas:
  sections:
    title: Sections area
    type: list
    availableComponents:
      a-section-news:
        id: jade:components/c-section-news
      a-section-works:
        id: jade:components/c-section-works
```

Jade template `/templates/t-homepage.jade`:
```jade-lang
extends ./base

block head
  script

block content
  h1 Hello #{content.title}
  +cms_area(name="sections")
  +cms_area(name="socials")

  footer
    p footer
```

Jade base layout `/templates/base.jade`:
```jade-lang
include /magnolia
doctype html

html
    head
        +cms_page
        title #{content.title} my jade template #{c}
        block head
    body
        .main
            .content
                block content
```
