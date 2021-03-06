/******************************************************************************
 * Copyright (C) 2019 by the ARA Contributors                                 *
 *                                                                            *
 * Licensed under the Apache License, Version 2.0 (the "License");            *
 * you may not use this file except in compliance with the License.           *
 * You may obtain a copy of the License at                                    *
 *                                                                            *
 * 	 http://www.apache.org/licenses/LICENSE-2.0                               *
 *                                                                            *
 * Unless required by applicable law or agreed to in writing, software        *
 * distributed under the License is distributed on an "AS IS" BASIS,          *
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   *
 * See the License for the specific language governing permissions and        *
 * limitations under the License.                                             *
 *                                                                            *
 ******************************************************************************/

package com.decathlon.ara.repository;

import com.decathlon.ara.domain.ProblemPattern;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ProblemPattern entity.
 */
@Repository
public interface ProblemPatternRepository extends JpaRepository<ProblemPattern, Long> {

    @Query("SELECT problemPattern " +
            "FROM ProblemPattern problemPattern " +
            "WHERE problemPattern.problem.projectId = ?1")
    List<ProblemPattern> findAllByProjectId(long projectId);

    @Query("SELECT problemPattern " +
            "FROM ProblemPattern problemPattern " +
            "WHERE problemPattern.problem.projectId = ?1 " +
            "AND problemPattern.id = ?2")
    ProblemPattern findByProjectIdAndId(long projectId, long id);

    boolean existsByCountryId(long countryId);

    boolean existsByTypeId(long typeId);

}
