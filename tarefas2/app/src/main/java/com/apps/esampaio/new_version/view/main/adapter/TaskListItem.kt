package com.apps.esampaio.new_version.view.main.adapter

import com.apps.esampaio.legacy.core.entities.Subtask
import com.apps.esampaio.legacy.core.entities.Task

data class TaskListItem(
    var task: Task,
    var subtask: Subtask,
    var isHeader: Boolean)
