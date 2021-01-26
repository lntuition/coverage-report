<#-- @ftlvariable name="modules" type="java.util.Collection<jetbrains.coverage.report.impl.html.ModuleInfo>" -->
<#-- @ftlvariable name="sortOption" type="jetbrains.coverage.report.impl.html.SortOption" -->
<#include "macros.ftl">

<@page title="Summary">
<div class="breadCrumbs">
    <@currentScope/>
    all ${resources['coverage.module_plural']}
</div>

<h1>Overall Coverage Summary</h1>
<@overallStatTable labelName="${resources['coverage.module']?cap_first}" labelValue="all classes" coverageStatistics=statsCalculator.overallStats/>

<br/>

<h2>Coverage Breakdown</h2>

<table class="coverageStats">
<#list modules as ms>
  <#if ms == modules?first>
  <tr>
    <th class="name  <@sortableCellClass sorted=sortOption.orderByName() sortedDesc=sortOption.descendingOrder/>">
      <@sortableCellLabel label=resources['coverage.module']?cap_first sortOption=sortOption.nextOrderByName()/>
    </th>
    <@coverageStatHeaderRow coverageStatistics=statsCalculator.getForModule(ms.name) sortOption=sortOption/>
  </tr>
  </#if>
  <tr>
    <td class="name"><a href="${paths.getNamespacesIndexPath(ms, sortOption)}"><@moduleName module=ms/></a></td>
    <@coverageStatRow coverageStatistics=statsCalculator.getForModule(ms.name)/>
  </tr>
</#list>
</table>
</@page>
