package com.github.crunc.springboot.cms.content.git

import org.eclipse.jgit.api.CreateBranchCommand.SetupUpstreamMode
import org.eclipse.jgit.api.Git
import org.eclipse.jgit.api.ListBranchCommand.ListMode
import org.eclipse.jgit.lib.Ref

internal fun Git.checkoutLabel(label: String): Ref {

    val checkout = checkout()

    if (isBranch(label) && !isLocalBranch(label)) {
        checkout.setCreateBranch(true)
                .setName(label)
                .setUpstreamMode(SetupUpstreamMode.TRACK)
                .setStartPoint("origin/$label")
    } else {
        checkout.setName(label)
    }

    return checkout.call()
}

internal fun Git.containsBranch(label: String, listMode: ListMode? = ListMode.ALL): Boolean {

    val command = branchList()

    if (listMode != null) {
        command.setListMode(listMode)
    }

    return command.call().any { branch -> branch.name.endsWith("/" + label) }
}

internal fun Git.isBranch(label: String): Boolean
        = containsBranch(label, ListMode.ALL)

internal fun Git.isLocalBranch(label: String): Boolean
        = containsBranch(label, null)