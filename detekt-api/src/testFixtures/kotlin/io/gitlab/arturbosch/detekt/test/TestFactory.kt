package io.gitlab.arturbosch.detekt.test

import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.RuleInstance
import io.gitlab.arturbosch.detekt.api.RuleSet
import io.gitlab.arturbosch.detekt.api.Severity
import io.gitlab.arturbosch.detekt.api.SourceLocation
import io.gitlab.arturbosch.detekt.api.TextLocation
import kotlin.io.path.Path

fun createIssue(
    ruleId: String = "TestSmell/id",
    entity: Issue.Entity = createEntity(),
    message: String = "TestMessage",
    severity: Severity = Severity.Error,
    suppressReasons: List<String> = emptyList(),
): Issue = createIssue(
    ruleInstance = createRuleInstance(ruleId),
    entity = entity,
    message = message,
    severity = severity,
    suppressReasons = suppressReasons,
)

fun createIssue(
    ruleInstance: RuleInstance,
    entity: Issue.Entity = createEntity(),
    message: String = "TestMessage",
    severity: Severity = Severity.Error,
    suppressReasons: List<String> = emptyList(),
): Issue = Issue(
    ruleInstance = ruleInstance,
    entity = entity,
    references = emptyList(),
    message = message,
    severity = severity,
    suppressReasons = suppressReasons,
)

fun createIssue(
    ruleInstance: RuleInstance,
    location: Issue.Location,
    message: String = "TestMessage",
    severity: Severity = Severity.Error,
    suppressReasons: List<String> = emptyList(),
): Issue = Issue(
    ruleInstance = ruleInstance,
    entity = createEntity(location = location),
    references = emptyList(),
    message = message,
    severity = severity,
    suppressReasons = suppressReasons,
)

fun createRuleInstance(
    id: String = "TestSmell/id",
    ruleSetId: String = "RuleSet${id.split("/", limit = 2).first()}",
    description: String = "Description ${id.split("/", limit = 2).first()}",
    severity: Severity = Severity.Error,
): RuleInstance {
    val split = id.split("/", limit = 2)
    return RuleInstance(
        id = id,
        name = Rule.Name(split.first()),
        ruleSetId = RuleSet.Id(ruleSetId),
        description = description,
        severity = severity,
    )
}

fun createEntity(
    signature: String = "TestEntitySignature",
    location: Issue.Location = createLocation(),
): Issue.Entity = Issue.Entity(
    signature = signature,
    location = location,
)

fun createLocation(
    path: String = "TestFile.kt",
    position: Pair<Int, Int> = 1 to 1,
    endPosition: Pair<Int, Int> = 1 to 1,
    text: IntRange = 0..0,
): Issue.Location {
    require(!path.startsWith("/")) { "The path shouldn't start with '/'" }
    return Issue.Location(
        source = SourceLocation(position.first, position.second),
        endSource = SourceLocation(endPosition.first, endPosition.second),
        text = TextLocation(text.first, text.last),
        path = Path(path),
    )
}
