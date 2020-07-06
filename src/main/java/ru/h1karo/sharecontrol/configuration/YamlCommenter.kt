/*
 * This file is a part of ShareControl.
 *
 * ShareControl is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * ShareControl is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with ShareControl. If not, see <https://www.gnu.org/licenses/>.
 *
 * @copyright Copyright (c) 2020 ShareControl
 * @author Oleg Kozlov <h1karo@outlook.com>
 * @license GNU General Public License v3.0
 * @link https://github.com/h1karo/sharecontrol
 */

package ru.h1karo.sharecontrol.configuration

import ru.h1karo.sharecontrol.configuration.entry.DescribedEntryInterface
import ru.h1karo.sharecontrol.configuration.entry.EntryInterface

class YamlCommenter {
    fun include(yaml: String, entries: Set<EntryInterface>): String {
        val comments: HashMap<String, String> = this.prepareComments(entries)
        if (comments.isEmpty()) {
            return yaml;
        }

        val lines = yaml.split(System.lineSeparator())
        return this.doInclude(lines, comments).joinToString(System.lineSeparator())
    }

    private fun doInclude(content: List<String>, comments: Map<String, String>): List<String> {
        val newContent = mutableListOf<String>()
        val currentPath = mutableListOf<String>()
        content.forEach {
            newContent.add(it)
        }
        return newContent
    }

    private fun prepareComments(entries: Set<EntryInterface>): HashMap<String, String> {
        val comments: HashMap<String, String> = HashMap()

        entries.forEach { entry ->
            if (entry !is DescribedEntryInterface) {
                return@forEach
            }

            val count = entry.getPath().count { path -> '.' == path }
            val leadingSpaces = " ".repeat(count)
            val comment = entry.getDescription()
                .joinToString(System.lineSeparator()) {
                    when {
                        it.isNotEmpty() -> "$leadingSpaces# $it"
                        else -> it
                    }
                }

            comments[entry.getPath()] = comment
        }

        return comments
    }
}