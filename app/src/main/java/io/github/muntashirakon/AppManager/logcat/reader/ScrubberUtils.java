/*
 * Copyright (c) 2021 Muntashir Al-Islam
 * Copyright (C) 2014 The CyanogenMod Project
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package io.github.muntashirakon.AppManager.logcat.reader;

import java.util.regex.Pattern;

public class ScrubberUtils {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("[a-zA-Z0-9_]+(?:\\.[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+)*(@|%40)(?!([a-zA-Z0-9]*\\.[a-zA-Z0-9]*\\.[a-zA-Z0-9]*\\.))(?:[A-Za-z0-9](?:[a-zA-Z0-9-]*[A-Za-z0-9])?\\.)+[a-zA-Z](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?");
    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("^(?:(?:\\+?1\\s*(?:[.-]\\s*)?)?(?:\\(\\s*([2-9]1[02-9]|[2-9][02-8]1|[2-9][02-8][02-9])\\s*\\)|([2-9]1[02-9]|[2-9][02-8]1|[2-9][02-8][02-9]))\\s*(?:[.-]\\s*)?)?([2-9]1[02-9]|[2-9][02-9]1|[2-9][02-9]{2})\\s*(?:[.-]\\s*)?([0-9]{4})(?:\\s*(?:#|x\\.?|ext\\.?|extension)\\s*(\\d+))?$");
    private static final Pattern WEB_URL_PATTERN = Pattern.compile("\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");
    private static final Pattern IP_ADDRESS_PATTERN = Pattern.compile("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
    private static final Pattern PHONE_INFO_PATTERN = Pattern.compile("(msisdn=|mMsisdn=|iccid=|iccid: |mImsi=)[a-zA-Z0-9]*", Pattern.CASE_INSENSITIVE);
    private static final Pattern USER_INFO_PATTERN = Pattern.compile("(UserInfo\\{\\d:)[a-zA-Z0-9\\s]*", Pattern.CASE_INSENSITIVE);
    private static final Pattern ACCOUNT_INFO_PATTERN = Pattern.compile("(Account \\{name=)[a-zA-Z0-9]*", Pattern.CASE_INSENSITIVE);

    private static final String IGNORE_DATA_RESOURCE_CACHE = "/data/resource-cache";
    private static final String IGNORE_DATA_DALVIK_CACHE = "/data/dalvik-cache";
    private static final String IGNORE_CACHE_DALVIK_CACHE = "/cache/dalvik-cache";

    public static String scrubLine(String line) {
        if (line.contains(IGNORE_DATA_RESOURCE_CACHE)
                || line.contains(IGNORE_DATA_DALVIK_CACHE)
                || line.contains(IGNORE_CACHE_DALVIK_CACHE)) {
            // Ugly work around :/
            return line;
        }
        line = IP_ADDRESS_PATTERN.matcher(line).replaceAll("<IP address omitted>");
        line = EMAIL_PATTERN.matcher(line).replaceAll("<email omitted>");
        line = PHONE_NUMBER_PATTERN.matcher(line).replaceAll("<phone number omitted>");
        line = WEB_URL_PATTERN.matcher(line).replaceAll("<web url omitted>");
        line = PHONE_INFO_PATTERN.matcher(line).replaceAll("<omitted>");
        line = USER_INFO_PATTERN.matcher(line).replaceAll("<omitted>");
        line = ACCOUNT_INFO_PATTERN.matcher(line).replaceAll("<omitted>");

        return line;
    }
}