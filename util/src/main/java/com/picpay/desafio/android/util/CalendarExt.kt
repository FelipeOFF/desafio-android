package com.picpay.desafio.android.util

import com.picpay.desafio.android.util.Const.LocaleConst.LOCALE_COUNTRY_BR
import com.picpay.desafio.android.util.Const.LocaleConst.LOCALE_PT
import java.util.Locale

val localeDefault: Locale by lazy {
    Locale(LOCALE_PT, LOCALE_COUNTRY_BR)
}