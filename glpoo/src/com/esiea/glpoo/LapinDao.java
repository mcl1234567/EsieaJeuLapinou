package com.esiea.glpoo;

/*
 * Copyright (C)1995-2013 icauda.com
 * Copyright (C)2012 Developpez.com
 *
 * Ces sources sont libres de droits, et vous pouvez les utiliser a votre 
 * convenance. 
 * 
 * http://www.icauda.com 
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.util.List;

/**
 * DAO pour la gestion des lapins.
 */
public interface LapinDao {

	/**
	 * Renvoie la liste complete des lapins.
	 * REGLE RF001 : Renvoie la liste complete des lapins.
	 * REGLE RF002 : Renvoie une liste vide s'il n'y a aucun lapin.
	 * @return */
	List<Lapin> findAllLapins();

	/**
	 * Recherche un lapin par son nom.
	 * REGLE RF003 : Renvoie le lapin trouve par son nom.
	 * REGLE RF003-B : Renvoie nul si aucun lapin n'est trouve.
	 * @param nom
	 * @return */
	Lapin findLapinByNom(final String nom);
}