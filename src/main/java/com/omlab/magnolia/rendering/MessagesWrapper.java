package com.omlab.magnolia.rendering;

import info.magnolia.i18nsystem.FixedLocaleProvider;
import info.magnolia.i18nsystem.LocaleProvider;
import info.magnolia.i18nsystem.TranslationService;
import info.magnolia.i18nsystem.util.MessageFormatterUtils;

import java.util.List;
import java.util.Locale;

/**
 * Utility class that has methods which allow passing multiple parameters
 * from a freemarker template to a message string using the square bracket
 * syntax (e.g. ${i18n.get('message', ['param1', 'param2']}). There are
 * convenience methods which allow selecting the message bundle directly
 * from within the template as well (by passing the basename parameter), but using those methods removed fallback to default message bundles so they should be used only when such custom bundle contains all messages requested in given context.
 */
public class MessagesWrapper {

  private final TranslationService translationService;
  private final LocaleProvider localeProvider;

  public MessagesWrapper(Locale locale, TranslationService translationService) {
    this.translationService = translationService;
    this.localeProvider = new FixedLocaleProvider(locale);
  }

  public String get(String key) {
    return translationService.translate(localeProvider, new String[]{key});
  }

  public String get(String key, List args) {
    Object[] argsArray = new Object[args.size()];
    return MessageFormatterUtils.format(this.get(key), localeProvider.getLocale(), args.toArray(argsArray));
  }

}
