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


/**
 * DAO pour la gestion des lapins.
 */
public interface JardinDao {

	/**
	 * Renvoie le jardin
	 * REGLE RF001 : Renvoie le jardin
	 * @return */
	Jardin findAllJardin();
}